package com.bootcamp.domain.usecase;

import com.bootcamp.domain.helper.constant.DomainConstants;
import com.bootcamp.domain.helper.exception.DuplicateTechnologyException;
import com.bootcamp.domain.helper.exception.TechnologyCountOutOfRangeException;
import com.bootcamp.domain.helper.exception.TechnologyNotExistsExceptions;
import com.bootcamp.domain.model.Technology;
import com.bootcamp.domain.spi.ITechnologyPersistencePort;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.stream.LongStream;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TechnologyUseCaseTest {

    @InjectMocks
    private TechnologyUseCase technologyUseCase;

    @Mock
    private ITechnologyPersistencePort technologyPersistencePort;

    private static Technology validTechnology;
    private static Technology createdTechnology;
    private static Technology technologyWithDuplicateName;
    private static List<Long> technologies;
    private static Flux<Technology> existingTechnologies;


    @BeforeAll
    static void setUp() {
        validTechnology = Technology.builder()
                .id(null)
                .name("Valid technology name")
                .description("Technology description")
                .build();

        createdTechnology = Technology.builder()
                .id(1L)
                .name("Valid technology name")
                .description("Technology description")
                .build();

        technologyWithDuplicateName = Technology.builder()
                .id(null)
                .name("Duplicate technology name")
                .description("Technology description")
                .build();

        technologies = LongStream
                .rangeClosed(1, 3)
                .boxed()
                .toList();

        existingTechnologies = Flux.fromIterable(technologies)
                .map(technologyId -> Technology.builder()
                        .id(technologyId)
                        .name("Technology name " + technologyId)
                        .description("Technology description " + technologyId)
                        .build());
    }

    @Test
    void shouldRegisterTechnologySuccessfully() {
        when(technologyPersistencePort.existsByName(validTechnology.getName()))
                .thenReturn(Mono.just(Boolean.FALSE));
        when(technologyPersistencePort.save(validTechnology))
                .thenReturn(Mono.just(createdTechnology));

        StepVerifier.create(technologyUseCase.registerTechnology(validTechnology))
                .expectNextMatches(technology ->
                        technology.getId().equals(createdTechnology.getId()) &&
                        technology.getName().equals(createdTechnology.getName()) &&
                        technology.getDescription().equals(createdTechnology.getDescription()))
                .expectComplete()
                .verify();
    }

    @Test
    void shouldReturnDuplicateTechnologyExceptionWhenTechnologyNameAlreadyExists() {
        when(technologyPersistencePort.existsByName(technologyWithDuplicateName.getName()))
                .thenReturn(Mono.just(Boolean.TRUE));

        StepVerifier.create(technologyUseCase.registerTechnology(technologyWithDuplicateName))
                .expectErrorMatches(error ->
                        error instanceof DuplicateTechnologyException &&
                        error.getMessage().equals(DomainConstants.DUPLICATE_TECHNOLOGY_MESSAGE))
                .verify();
    }

    @Test
    void shouldValidateCapabilityTechnologiesSuccessfully() {
        when(technologyPersistencePort.findAllByIds(technologies))
                .thenReturn(existingTechnologies);

        StepVerifier.create(technologyUseCase.validateCapabilityTechnologies(technologies))
                .expectNextMatches(list -> list.size() == technologies.size())
                .expectComplete()
                .verify();
    }

    @Test
    void shouldThrowTechnologyCountOutOfRangeExceptionWhenTechnologyIdsSizeIsLessThanMinimum() {
        technologies = LongStream
                .rangeClosed(1, DomainConstants.LOWER_LIMIT - 1)
                .boxed()
                .toList();

        StepVerifier.create(technologyUseCase.validateCapabilityTechnologies(technologies))
                .expectErrorMatches(error -> error instanceof TechnologyCountOutOfRangeException &&
                                             error.getMessage().equals(DomainConstants.TECHNOLOGY_LIMITS_MESSAGE))
                .verify();
    }

    @Test
    void shouldThrowTechnologyCountOutOfRangeExceptionWhenTechnologyIdsSizeIsGreaterThanMaximum() {
        technologies = LongStream
                .rangeClosed(1, DomainConstants.UPPER_LIMIT + 1)
                .boxed()
                .toList();

        StepVerifier.create(technologyUseCase.validateCapabilityTechnologies(technologies))
                .expectErrorMatches(error -> error instanceof TechnologyCountOutOfRangeException &&
                                             error.getMessage().equals(DomainConstants.TECHNOLOGY_LIMITS_MESSAGE))
                .verify();
    }

    @Test
    void shouldNotThrowExceptionWhenTechnologyIdsSizeIsExactlyLowerLimit() {
        technologies = LongStream
                .rangeClosed(1, DomainConstants.LOWER_LIMIT)
                .boxed()
                .toList();

        existingTechnologies = Flux.fromIterable(technologies)
                .map(technologyId -> Technology.builder()
                        .id(technologyId)
                        .name("Technology name " + technologyId)
                        .description("Technology description " + technologyId)
                        .build());

        when(technologyPersistencePort.findAllByIds(technologies))
                .thenReturn(existingTechnologies);

        StepVerifier.create(technologyUseCase.validateCapabilityTechnologies(technologies))
                .expectNextMatches(list -> list.size() == DomainConstants.LOWER_LIMIT)
                .verifyComplete();
    }

    @Test
    void shouldNotThrowExceptionWhenTechnologyIdsSizeIsExactlyUpperLimit() {
        technologies = LongStream
                .rangeClosed(1, DomainConstants.UPPER_LIMIT)
                .boxed()
                .toList();

        existingTechnologies = Flux.fromIterable(technologies)
                .map(technologyId -> Technology.builder()
                        .id(technologyId)
                        .name("Technology name " + technologyId)
                        .description("Technology description " + technologyId)
                        .build());

        when(technologyPersistencePort.findAllByIds(technologies))
                .thenReturn(existingTechnologies);

        StepVerifier.create(technologyUseCase.validateCapabilityTechnologies(technologies))
                .expectNextMatches(list -> list.size() == DomainConstants.UPPER_LIMIT)
                .verifyComplete();
    }

    @Test
    void shouldThrowDuplicateTechnologyExceptionWhenTechnologyIdsHaveDuplicates() {
        technologies = List.of(1L, 2L, 3L, 3L);

        StepVerifier.create(technologyUseCase.validateCapabilityTechnologies(technologies))
                .expectErrorMatches(error -> error instanceof DuplicateTechnologyException &&
                                             error.getMessage().equals(DomainConstants.DUPLICATE_TECHNOLOGIES_FOUND_MESSAGE))
                .verify();
    }

    @Test
    void shouldThrowTechnologyNotExistsExceptionsWhenAtLessTechnologyDoesNotExist() {
        long validLimit = 10L;
        technologies = LongStream
                .rangeClosed(1, validLimit)
                .boxed()
                .toList();

        existingTechnologies = Flux.fromIterable(technologies)
                .skip(1)
                .map(technologyId -> Technology.builder()
                        .id(technologyId)
                        .name("Technology name " + technologyId)
                        .description("Technology description " + technologyId)
                        .build());

        when(technologyPersistencePort.findAllByIds(technologies))
                .thenReturn(existingTechnologies);

        StepVerifier.create(technologyUseCase.validateCapabilityTechnologies(technologies))
                .expectErrorMatches(error -> error instanceof TechnologyNotExistsExceptions &&
                                             error.getMessage().equals(DomainConstants.TECHNOLOGIES_NOT_EXISTS_MESSAGE))
                .verify();
    }
}