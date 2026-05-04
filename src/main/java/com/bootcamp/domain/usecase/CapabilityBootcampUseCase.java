package com.bootcamp.domain.usecase;

import com.bootcamp.domain.api.ICapabilityBootcampServicePort;
import com.bootcamp.domain.spi.ICapabilityBootcampPersistencePort;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.List;

@RequiredArgsConstructor
public class CapabilityBootcampUseCase implements ICapabilityBootcampServicePort {

    private final ICapabilityBootcampPersistencePort capabilityBootcampPersistencePort;

    @Override
    public Mono<Void> createCapabilityBootcamp(Long bootcampId, List<Long> capabilityIds) {
        return capabilityBootcampPersistencePort.save(bootcampId, capabilityIds);
    }
}
