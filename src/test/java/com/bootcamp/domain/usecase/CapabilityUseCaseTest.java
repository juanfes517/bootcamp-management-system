package com.bootcamp.domain.usecase;

import com.bootcamp.domain.model.Capability;
import com.bootcamp.domain.spi.ICapabilityPersistencePort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

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

}