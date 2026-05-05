package com.bootcamp.domain.usecase;

import com.bootcamp.domain.spi.ICapabilityBootcampPersistencePort;
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
class CapabilityBootcampUseCaseTest {

    @InjectMocks
    private CapabilityBootcampUseCase capabilityBootcampUseCase;

    @Mock
    private ICapabilityBootcampPersistencePort capabilityBootcampPersistencePort;

    @Test
    void shouldCreateCapabilityBootcamp() {
        Long bootcampId = 1L;
        List<Long> capabilityIds = List.of(1L, 2L);

        when(capabilityBootcampPersistencePort.save(bootcampId, capabilityIds))
                .thenReturn(Mono.empty());

        StepVerifier.create(capabilityBootcampUseCase.createCapabilityBootcamp(bootcampId, capabilityIds))
                .verifyComplete();
    }

}