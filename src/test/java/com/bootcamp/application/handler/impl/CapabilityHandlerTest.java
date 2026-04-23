package com.bootcamp.application.handler.impl;

import com.bootcamp.domain.api.ICapabilityServicePort;
import com.bootcamp.domain.api.ITechnologyCapabilityServicePort;
import com.bootcamp.domain.api.ITechnologyServicePort;
import com.bootcamp.domain.model.Capability;
import com.bootcamp.domain.model.Technology;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CapabilityHandlerTest {

    @InjectMocks
    private CapabilityHandler capabilityHandler;

    @Mock
    private ICapabilityServicePort capabilityServicePort;

    @Mock
    private ITechnologyServicePort technologyServicePort;

    @Mock
    private ITechnologyCapabilityServicePort technologyCapabilityServicePort;

    @Test
    void shouldRegisterCapabilitySuccessfully() {
        Capability newCapability = Capability.builder()
                .name("Capability name")
                .description("Capability description")
                .build();

        Capability createdCapability = Capability.builder()
                .id(1L)
                .name("Capability name")
                .description("Capability description")
                .build();

        List<Long> technologiesIds = List.of(1L, 2L);

        List<Technology> technologies = technologiesIds.stream()
                .map(technologyId -> Technology.builder()
                        .id(technologyId)
                        .name("Technology name" + technologyId)
                        .description("Technology description" + technologyId)
                        .build())
                .toList();

        when(technologyServicePort.validateCapabilityTechnologies(technologiesIds))
                .thenReturn(Mono.just(technologies));
        when(capabilityServicePort.registerCapability(newCapability))
                .thenReturn(Mono.just(createdCapability));
        when(technologyCapabilityServicePort.createTechnologyCapability(createdCapability.getId(), technologiesIds))
                .thenReturn(Mono.empty());

        StepVerifier.create(capabilityHandler.registerCapability(newCapability, technologiesIds))
                .expectNextMatches(capability -> capability.getId().equals(createdCapability.getId()) &&
                        capability.getName().equals(createdCapability.getName()) &&
                        capability.getDescription().equals(createdCapability.getDescription()) &&
                        capability.getTechnologyList().size() == createdCapability.getTechnologyList().size())
                .verifyComplete();
    }
}