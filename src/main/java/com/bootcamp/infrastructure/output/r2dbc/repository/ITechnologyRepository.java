package com.bootcamp.infrastructure.output.r2dbc.repository;

import com.bootcamp.infrastructure.output.r2dbc.entity.TechnologyEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

public interface ITechnologyRepository extends R2dbcRepository<TechnologyEntity, Long> {

    Mono<Boolean> existsByName(String name);
}
