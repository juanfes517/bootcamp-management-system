package com.bootcamp.domain.spi;

import com.bootcamp.domain.model.Bootcamp;
import com.bootcamp.domain.model.PageRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface IBootcampPersistencePort {

    Mono<Bootcamp> save(Bootcamp bootcamp);
    Flux<Bootcamp> findAll(PageRequest pageRequest);
    Mono<Bootcamp> findById(Long id);
}
