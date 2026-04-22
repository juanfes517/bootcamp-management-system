package com.bootcamp.domain.spi;

import com.bootcamp.domain.model.Capability;
import reactor.core.publisher.Mono;

public interface ICapabilityPersistencePort {

    Mono<Capability> save(Capability capability);
}
