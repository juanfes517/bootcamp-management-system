package com.bootcamp.domain.spi;

import com.bootcamp.domain.model.Technology;
import reactor.core.publisher.Mono;

public interface ITechnologyPersistencePort {

    Mono<Technology> save(Technology technology);
    Mono<Boolean> existsByName(String name);
}
