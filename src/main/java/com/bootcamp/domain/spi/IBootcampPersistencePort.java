package com.bootcamp.domain.spi;

import com.bootcamp.domain.model.Bootcamp;
import com.bootcamp.domain.model.PageRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IBootcampPersistencePort {

    Mono<Bootcamp> save(Bootcamp bootcamp);
    Flux<Bootcamp> findAll(PageRequest pageRequest);
}
