package com.bootcamp.domain.usecase;

import com.bootcamp.domain.api.IBootcampServicePort;
import com.bootcamp.domain.helper.constant.DomainConstants;
import com.bootcamp.domain.helper.exception.BootcampCountOutOfRangeException;
import com.bootcamp.domain.helper.exception.BootcampNotExistsException;
import com.bootcamp.domain.helper.exception.DateConflictException;
import com.bootcamp.domain.helper.exception.DuplicateBootcampsException;
import com.bootcamp.domain.model.Bootcamp;
import com.bootcamp.domain.model.PageRequest;
import com.bootcamp.domain.model.RegistrationResult;
import com.bootcamp.domain.model.UserBootcampRegistration;
import com.bootcamp.domain.spi.IBootcampPersistencePort;
import com.bootcamp.domain.spi.IUserExternalServicePort;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
public class BootcampUseCase implements IBootcampServicePort {

    private final IBootcampPersistencePort bootcampPersistencePort;
    private final IUserExternalServicePort userExternalServicePort;

    @Override
    public Mono<Bootcamp> registerBootcamp(Bootcamp bootcamp) {
        return bootcampPersistencePort.save(bootcamp);
    }

    @Override
    public Flux<Bootcamp> getAllBootcamps(PageRequest pageRequest) {
        return bootcampPersistencePort.findAll(pageRequest);
    }

    @Override
    public Flux<RegistrationResult> registerUser(UserBootcampRegistration userBootcampRegistration) {
        return Mono.just(userBootcampRegistration.getBootcampIds())
                .flatMap(this::checkNumberOfBootcamps)
                .flatMap(this::checkDuplicateBootcamps)
                .flatMapMany(Flux::fromIterable)
                .flatMap(this::findBootcamp)
                .collectList()
                .flatMap(this::checkDates)
                .flatMapMany(bootcamps ->
                        this.sendBootcampsToUserService(bootcamps, userBootcampRegistration.getEmail()));
    }

    private Flux<RegistrationResult> sendBootcampsToUserService(List<RegistrationResult> resultBootcamps, String email) {
        List<Bootcamp> bootcamps = this.filterOnlySuccessBootcamps(resultBootcamps);

        return userExternalServicePort.registerBootcamps(bootcamps, email)
                .thenMany(Flux.fromIterable(resultBootcamps));
    }

    private Mono<RegistrationResult> findBootcamp(Long id) {
        return bootcampPersistencePort.findById(id)
                .map(bootcamp -> new RegistrationResult(bootcamp, true, null))
                .onErrorResume(BootcampNotExistsException.class,
                        ex -> Mono.just(new RegistrationResult(id, false, ex.getMessage())));
    }

    private Mono<List<RegistrationResult>> checkDates(List<RegistrationResult> resultBootcamps) {
        List<Bootcamp> bootcamps = this.filterOnlySuccessBootcamps(resultBootcamps);

        for (int i = 0; i<bootcamps.size(); i++) {
            Bootcamp bootcamp1 = bootcamps.get(i);
            for (int j = i+1; j<bootcamps.size(); j++) {
                Bootcamp bootcamp2 = bootcamps.get(j);
                if (this.overlap(bootcamp1, bootcamp2)) {
                    return Mono.error(new DateConflictException(List.of(bootcamp1.getId(), bootcamp2.getId())));
                }
            }
        }

        return Mono.just(resultBootcamps);
    }

    private List<Bootcamp> filterOnlySuccessBootcamps(List<RegistrationResult> resultBootcamps) {
        return resultBootcamps.stream()
                .filter(RegistrationResult::isSuccess)
                .map(bootcamp -> (Bootcamp) bootcamp.getBootcamp())
                .toList();
    }

    private boolean overlap(Bootcamp bootcamp1, Bootcamp bootcamp2) {
        LocalDateTime endDate1 = bootcamp1.getReleaseDate().plusDays(bootcamp1.getDurationDays());
        LocalDateTime endDate2 = bootcamp2.getReleaseDate().plusDays(bootcamp2.getDurationDays());
        return endDate1.isAfter(bootcamp2.getReleaseDate()) && endDate2.isAfter(bootcamp1.getReleaseDate());
    }

    private Mono<List<Long>> checkNumberOfBootcamps(List<Long> bootcamps) {
        if (bootcamps.isEmpty() || bootcamps.size() > 5) {
            return Mono.error(new BootcampCountOutOfRangeException(
                    DomainConstants.BOOTCAMP_LIMITS_MESSAGE,
                    bootcamps.size()
            ));
        }

        return Mono.just(bootcamps);
    }

    private Mono<List<Long>> checkDuplicateBootcamps(List<Long> bootcamps) {
        Set<Long> uniquesBootcamps = new HashSet<>();
        List<Long> duplicates = bootcamps.stream()
                .filter(item -> !uniquesBootcamps.add(item))
                .distinct()
                .toList();

        if (!duplicates.isEmpty()) {
            return Mono.error(new DuplicateBootcampsException(DomainConstants.DUPLICATE_BOOTCAMP_MESSAGE, duplicates));
        }

        return Mono.just(bootcamps);
    }
}
