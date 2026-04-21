package com.bootcamp.domain.api;

import com.bootcamp.domain.model.Technology;
import reactor.core.publisher.Mono;

public interface ITechnologyServicePort {

    Mono<Technology> registerTechnology(Technology technology);
}
