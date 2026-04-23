package com.bootcamp.domain.usecase;

import com.bootcamp.domain.spi.ITechnologyCapabilityPersistencePort;
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
class TechnologyCapabilityUseCaseTest {

    @InjectMocks
    private TechnologyCapabilityUseCase technologyCapabilityUseCase;

    @Mock
    private ITechnologyCapabilityPersistencePort technologyCapabilityPersistencePort;

    @Test
    void ShouldCreateTechnologyCapabilitySuccessfully() {
        Long capabilityId = 1L;
        List<Long> technologiesIds = List.of(1L, 2L);

        when(technologyCapabilityPersistencePort.save(capabilityId, technologiesIds))
                .thenReturn(Mono.empty());

        StepVerifier.create(technologyCapabilityUseCase.createTechnologyCapability(capabilityId, technologiesIds))
                .expectNextCount(0)
                .verifyComplete();
    }
}