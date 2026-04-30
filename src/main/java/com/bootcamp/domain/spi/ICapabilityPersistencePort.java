package com.bootcamp.domain.spi;

import com.bootcamp.domain.model.Capability;
import com.bootcamp.domain.model.PageRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ICapabilityPersistencePort {

    Mono<Capability> save(Capability capability);
    Flux<Capability> findAll(PageRequest pageRequest);
}
