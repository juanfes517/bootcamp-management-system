package com.bootcamp.domain.usecase;

import com.bootcamp.domain.helper.constant.DomainConstants;
import com.bootcamp.domain.helper.exception.BootcampCountOutOfRangeException;
import com.bootcamp.domain.helper.exception.BootcampNotExistsException;
import com.bootcamp.domain.helper.exception.DateConflictException;
import com.bootcamp.domain.helper.exception.DuplicateBootcampsException;
import com.bootcamp.domain.model.*;
import com.bootcamp.domain.spi.IBootcampPersistencePort;
import com.bootcamp.domain.spi.IUserExternalServicePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BootcampUseCaseTest {

    @InjectMocks
    private BootcampUseCase bootcampUseCase;

    @Mock
    private IBootcampPersistencePort bootcampPersistencePort;

    @Mock
    private IUserExternalServicePort userExternalServicePort;


    private Bootcamp bootcamp1;
    private Bootcamp bootcamp2;
    private Bootcamp bootcamp3;
    private Bootcamp bootcamp4;
    private Bootcamp bootcamp5;
    private Bootcamp bootcamp6;

    @BeforeEach
    void setUp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String date1 = "2026-09-01 08:00:00";
        String date2 = "2026-10-01 08:00:00";
        String date3 = "2026-11-01 08:00:00";
        String date4 = "2026-12-01 08:00:00";
        String date5 = "2027-01-01 08:00:00";
        String date6 = "2026-09-05 08:00:00";

        bootcamp1 = Bootcamp.builder()
                .id(1L)
                .description("Description 1")
                .releaseDate(LocalDateTime.parse(date1, formatter))
                .durationDays(10)
                .build();

        bootcamp2 = Bootcamp.builder()
                .id(2L)
                .description("Description 2")
                .releaseDate(LocalDateTime.parse(date2, formatter))
                .durationDays(10)
                .build();

        bootcamp3 = Bootcamp.builder()
                .id(3L)
                .description("Description 3")
                .releaseDate(LocalDateTime.parse(date3, formatter))
                .durationDays(10)
                .build();

        bootcamp4 = Bootcamp.builder()
                .id(4L)
                .description("Description 4")
                .releaseDate(LocalDateTime.parse(date4, formatter))
                .durationDays(10)
                .build();

        bootcamp5 = Bootcamp.builder()
                .id(5L)
                .description("Description 5")
                .releaseDate(LocalDateTime.parse(date5, formatter))
                .durationDays(10)
                .build();

        bootcamp6 = Bootcamp.builder()
                .id(6L)
                .description("Description 6")
                .releaseDate(LocalDateTime.parse(date6, formatter))
                .durationDays(10)
                .build();
    }

    @Test
    void shouldSaveBootcampSuccessfully() {
        Bootcamp newBootcamp = Bootcamp.builder()
                .name("Bootcamp name")
                .description("Bootcamp description")
                .releaseDate(LocalDateTime.now())
                .durationDays(10)
                .build();

        Bootcamp savedBootcamp = Bootcamp.builder()
                .id(1L)
                .name("Bootcamp name")
                .description("Bootcamp description")
                .releaseDate(LocalDateTime.now())
                .durationDays(10)
                .build();

        when(bootcampPersistencePort.save(newBootcamp))
                .thenReturn(Mono.just(savedBootcamp));

        StepVerifier.create(bootcampUseCase.registerBootcamp(newBootcamp))
                .expectNextMatches(bootcamp -> bootcamp.getId().equals(savedBootcamp.getId()) &&
                                               bootcamp.getName().equals(savedBootcamp.getName()) &&
                                               bootcamp.getDescription().equals(savedBootcamp.getDescription()) &&
                                               bootcamp.getReleaseDate().equals(savedBootcamp.getReleaseDate()) &&
                                               bootcamp.getDurationDays() == savedBootcamp.getDurationDays())
                .verifyComplete();
    }

    @Test
    void shouldGetAllBootcampsSuccessfully() {
        PageRequest pageRequest = PageRequest.builder()
                .page(0)
                .size(2)
                .sortBy("name")
                .sortOrder(PageRequest.SortOrder.DESC)
                .build();

        Bootcamp bootcamp1 = Bootcamp.builder()
                .id(1L)
                .name("Bootcamp 1")
                .description("Description 1")
                .releaseDate(LocalDateTime.now())
                .durationDays(10)
                .capabilityList(List.of(new Capability(), new Capability()))
                .build();

        Bootcamp bootcamp2 = Bootcamp.builder()
                .id(1L)
                .name("Bootcamp 2")
                .description("Description 2")
                .releaseDate(LocalDateTime.now())
                .durationDays(10)
                .capabilityList(List.of(new Capability(), new Capability()))
                .build();

        Flux<Bootcamp> bootcamps = Flux.just(bootcamp1, bootcamp2);

        when(bootcampPersistencePort.findAll(pageRequest))
                .thenReturn(bootcamps);

        StepVerifier.create(bootcampUseCase.getAllBootcamps(pageRequest))
                .expectNextMatches(bootcamp -> bootcamp.getId().equals(bootcamp1.getId()))
                .expectNextMatches(bootcamp -> bootcamp.getId().equals(bootcamp2.getId()))
                .verifyComplete();
    }

    @Test
    void shouldRegisterUserSuccessfully() {
        String email = "test@email.com";

        UserBootcampRegistration userBootcampRegistration = UserBootcampRegistration.builder()
                .email(email)
                .bootcampIds(List.of(1L, 2L, 3L, 4L, 5L))
                .build();

        when(bootcampPersistencePort.findById(1L))
                .thenReturn(Mono.just(bootcamp1));
        when(bootcampPersistencePort.findById(2L))
                .thenReturn(Mono.just(bootcamp2));
        when(bootcampPersistencePort.findById(3L))
                .thenReturn(Mono.just(bootcamp3));
        when(bootcampPersistencePort.findById(4L))
                .thenReturn(Mono.just(bootcamp4));
        when(bootcampPersistencePort.findById(5L))
                .thenReturn(Mono.just(bootcamp5));
        when(userExternalServicePort.registerBootcamps(List.of(bootcamp1, bootcamp2, bootcamp3, bootcamp4, bootcamp5), email))
                .thenReturn(Mono.just("Success message"));

        StepVerifier.create(bootcampUseCase.registerUser(userBootcampRegistration))
                .expectNextMatches(result -> ((Bootcamp) result.getBootcamp()).getId() == 1L)
                .expectNextMatches(result -> ((Bootcamp) result.getBootcamp()).getId() == 2L)
                .expectNextMatches(result -> ((Bootcamp) result.getBootcamp()).getId() == 3L)
                .expectNextMatches(result -> ((Bootcamp) result.getBootcamp()).getId() == 4L)
                .expectNextMatches(result -> ((Bootcamp) result.getBootcamp()).getId() == 5L)
                .verifyComplete();
    }

    @Test
    void shouldThrowBootcampCountOutOfRangeExceptionWhenHaveSixBootcampIds() {
        UserBootcampRegistration userBootcampRegistration = UserBootcampRegistration.builder()
                .email("test@email.com")
                .bootcampIds(List.of(1L, 2L, 3L, 4L, 5L, 6L))
                .build();

        StepVerifier.create(bootcampUseCase.registerUser(userBootcampRegistration))
                .expectErrorMatches(error -> error instanceof BootcampCountOutOfRangeException &&
                        error.getMessage().equals(DomainConstants.BOOTCAMP_LIMITS_MESSAGE))
                .verify();
    }

    @Test
    void shouldThrowBootcampCountOutOfRangeExceptionWhenHaveZeroBootcampIds() {
        UserBootcampRegistration userBootcampRegistration = UserBootcampRegistration.builder()
                .email("test@email.com")
                .bootcampIds(List.of())
                .build();

        StepVerifier.create(bootcampUseCase.registerUser(userBootcampRegistration))
                .expectErrorMatches(error -> error instanceof BootcampCountOutOfRangeException &&
                                             error.getMessage().equals(DomainConstants.BOOTCAMP_LIMITS_MESSAGE))
                .verify();
    }

    @Test
    void shouldThrowBootcampNotExistsExceptionWhenIdDoesNotExist() {
        Long idDoesNotExist = 100L;
        String email = "test@email.com";

        UserBootcampRegistration userBootcampRegistration = UserBootcampRegistration.builder()
                .email(email)
                .bootcampIds(List.of(idDoesNotExist))
                .build();

        when(bootcampPersistencePort.findById(idDoesNotExist))
                .thenReturn(Mono.error(new BootcampNotExistsException(idDoesNotExist)));
        when(userExternalServicePort.registerBootcamps(List.of(), email))
                .thenReturn(Mono.just("Success message"));

        StepVerifier.create(bootcampUseCase.registerUser(userBootcampRegistration))
                .expectNextMatches(result -> Objects.equals(result.getBootcamp(), idDoesNotExist))
                .verifyComplete();
    }

    @Test
    void shouldThrowDateConflictExceptionWhenDatesOverlap() {
        String email = "test@email.com";

        UserBootcampRegistration userBootcampRegistration = UserBootcampRegistration.builder()
                .email(email)
                .bootcampIds(List.of(1L, 6L))
                .build();

        when(bootcampPersistencePort.findById(1L))
                .thenReturn(Mono.just(bootcamp1));
        when(bootcampPersistencePort.findById(6L))
                .thenReturn(Mono.just(bootcamp6));

        StepVerifier.create(bootcampUseCase.registerUser(userBootcampRegistration))
                .expectErrorMatches(error -> error instanceof DateConflictException &&
                        error.getMessage().equals(DomainConstants.DATE_CONFLICT_MESSAGE))
                .verify();
    }

    @Test
    void shouldThrowDuplicateBootcampsExceptionWhenHavaDuplicateBootcampIds() {
        String email = "test@email.com";

        UserBootcampRegistration userBootcampRegistration = UserBootcampRegistration.builder()
                .email(email)
                .bootcampIds(List.of(1L, 1L))
                .build();

        StepVerifier.create(bootcampUseCase.registerUser(userBootcampRegistration))
                .expectErrorMatches(error -> error instanceof DuplicateBootcampsException &&
                                             error.getMessage().equals(DomainConstants.DUPLICATE_BOOTCAMP_MESSAGE))
                .verify();
    }
}