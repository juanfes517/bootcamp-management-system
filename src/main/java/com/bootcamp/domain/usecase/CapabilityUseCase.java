package com.bootcamp.domain.usecase;

import com.bootcamp.domain.api.ICapabilityServicePort;
import com.bootcamp.domain.model.Capability;
import com.bootcamp.domain.spi.ICapabilityPersistencePort;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class CapabilityUseCase implements ICapabilityServicePort {

    private final ICapabilityPersistencePort capabilityPersistencePort;

    @Override
    public Mono<Capability> registerCapability(Capability capability) {
        return capabilityPersistencePort.save(capability);
    }

}
