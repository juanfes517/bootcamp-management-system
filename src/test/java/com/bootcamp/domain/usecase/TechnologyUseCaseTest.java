package com.bootcamp.domain.usecase;

import com.bootcamp.domain.helper.constant.DomainConstants;
import com.bootcamp.domain.helper.exception.DuplicateTechnologyException;
import com.bootcamp.domain.model.Technology;
import com.bootcamp.domain.spi.ITechnologyPersistencePort;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
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
}