package com.bootcamp.domain.usecase;

import com.bootcamp.domain.api.ITechnologyServicePort;
import com.bootcamp.domain.helper.constant.DomainConstants;
import com.bootcamp.domain.helper.exception.DuplicateTechnologyException;
import com.bootcamp.domain.helper.exception.TechnologyCountOutOfRangeException;
import com.bootcamp.domain.helper.exception.TechnologyNotExistsExceptions;
import com.bootcamp.domain.model.Technology;
import com.bootcamp.domain.spi.ITechnologyPersistencePort;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class TechnologyUseCase implements ITechnologyServicePort {

    private final ITechnologyPersistencePort technologyPersistencePort;

    public Mono<Technology> registerTechnology(Technology technology) {
        return technologyPersistencePort
                .existsByName(technology.getName())
                .filter(Boolean.FALSE::equals)
                .switchIfEmpty(Mono.error(new DuplicateTechnologyException(technology.getName())))
                .flatMap(exists -> technologyPersistencePort.save(technology));
    }

    @Override
    public Mono<List<Technology>> validateCapabilityTechnologies(List<Long> technologies) {
        return Mono.just(technologies)
                .flatMap(this::validateTechnologyLimits)
                .flatMap(this::validateNoDuplicateTechnologies)
                .flatMap(this::validateTechnologiesExist);
    }

    private Mono<List<Technology>> validateTechnologiesExist(List<Long> technologies) {
        return technologyPersistencePort.findAllByIds(technologies)
                .collectList()
                .flatMap(existingTechnologies  -> {
                    List<Long> existingIds = existingTechnologies.stream()
                            .map(Technology::getId)
                            .toList();

                    List<Long> missingIds = technologies.stream()
                            .filter(id -> !existingIds.contains(id))
                            .toList();

                    if (!missingIds.isEmpty()) {
                        return Mono.error(new TechnologyNotExistsExceptions(missingIds.toString()));
                    }

                    return Mono.just(existingTechnologies);
                });
    }

    private Mono<List<Long>> validateTechnologyLimits(List<Long> technologies) {
        if (technologies.size() < 3 || technologies.size() > 20) {
            return Mono.error(new TechnologyCountOutOfRangeException(
                    DomainConstants.TECHNOLOGY_LIMITS_MESSAGE,
                    technologies.size()));
        }
        return Mono.just(technologies);
    }

    private Mono<List<Long>> validateNoDuplicateTechnologies(List<Long> technologies) {
        Set<Long> duplicates = technologies.stream()
                .collect(Collectors.groupingBy(id -> id, Collectors.counting()))
                .entrySet().stream()
                .filter(entry -> entry.getValue() > 1)
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());

        if (!duplicates.isEmpty()) {
            return Mono.error(new DuplicateTechnologyException(
                    DomainConstants.DUPLICATE_TECHNOLOGIES_FOUND_MESSAGE,
                    duplicates.toString()));
        }

        return Mono.just(technologies);
    }


}
