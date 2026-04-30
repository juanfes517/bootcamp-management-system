package com.bootcamp.domain.usecase;

import com.bootcamp.domain.model.Capability;
import com.bootcamp.domain.model.PageRequest;
import com.bootcamp.domain.model.Technology;
import com.bootcamp.domain.spi.ICapabilityPersistencePort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class CapabilityUseCaseTest {

    @InjectMocks
    private CapabilityUseCase capabilityUseCase;

    @Mock
    private ICapabilityPersistencePort capabilityPersistencePort;

    @Test
    void shouldRegisterCapabilitySuccessfully() {
        Capability newCapability = Capability.builder()
                .name("Capability name")
                .description("Capability description")
                .build();

        Capability capability = Capability.builder()
                .id(1L)
                .name("Capability name")
                .description("Capability description")
                .build();

        when(capabilityPersistencePort.save(newCapability))
                .thenReturn(Mono.just(capability));

        StepVerifier.create(capabilityUseCase.registerCapability(newCapability))
                .expectNextMatches(result ->
                        result.getId().equals(capability.getId()) &&
                        result.getName().equals(capability.getName()))
                .verifyComplete();

    }

    @Test
    void shouldFindAllCapabilitiesSuccessfully() {
        Technology tech1 = Technology.builder()
                .id(1L)
                .build();

        Technology tech2 = Technology.builder()
                .id(2L)
                .build();

        Capability capability1 = Capability.builder()
                .id(1L)
                .name("Capability name 1")
                .description("Capability description 1")
                .technologyList(List.of(tech1, tech2))
                .build();

        Capability capability2 = Capability.builder()
                .id(2L)
                .name("Capability name 2")
                .description("Capability description 2")
                .technologyList(List.of())
                .build();

        PageRequest pageRequest = PageRequest.builder()
                .page(0)
                .size(2)
                .build();

        when(capabilityPersistencePort.findAll(pageRequest))
                .thenReturn(Flux.just(capability1, capability2));

        StepVerifier.create(capabilityUseCase.getAllCapabilities(pageRequest))
                .expectNextMatches(capability -> capability.getId().equals(capability1.getId()))
                .expectNextMatches(capability -> capability.getId().equals(capability2.getId()))
                .verifyComplete();
    }

}