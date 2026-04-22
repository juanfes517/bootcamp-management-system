package com.bootcamp.domain.usecase;

import com.bootcamp.domain.api.ITechnologyCapabilityServicePort;
import com.bootcamp.domain.spi.ITechnologyCapabilityPersistencePort;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.List;

@RequiredArgsConstructor
public class TechnologyCapabilityUseCase implements ITechnologyCapabilityServicePort {

    private final ITechnologyCapabilityPersistencePort technologyCapabilityPersistencePort;

    @Override
    public Mono<Void> createTechnologyCapability(Long capabilityId, List<Long> technologiesIds) {
        return technologyCapabilityPersistencePort.save(capabilityId, technologiesIds);
    }
}
