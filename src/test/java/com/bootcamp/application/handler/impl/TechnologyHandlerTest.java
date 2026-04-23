package com.bootcamp.application.handler.impl;

import com.bootcamp.domain.api.ITechnologyServicePort;
import com.bootcamp.domain.model.Technology;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TechnologyHandlerTest {

    @InjectMocks
    private TechnologyHandler technologyHandler;

    @Mock
    private ITechnologyServicePort technologyServicePort;

    private static Technology validTechnology;
    private static Technology createdTechnology;


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
    }

    @Test
    void ShouldRegisterTechnologySuccessfully() {
        when(technologyServicePort.registerTechnology(validTechnology))
                .thenReturn(Mono.just(createdTechnology));

        StepVerifier.create(technologyHandler.registerTechnology(validTechnology))
                .expectNextMatches(technology ->
                        technology.getId().equals(createdTechnology.getId()) &&
                        technology.getName().equals(createdTechnology.getName()) &&
                        technology.getDescription().equals(createdTechnology.getDescription()))
                .expectComplete()
                .verify();

    }
}