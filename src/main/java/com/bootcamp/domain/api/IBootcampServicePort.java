package com.bootcamp.domain.api;

import com.bootcamp.domain.model.Bootcamp;
import reactor.core.publisher.Mono;

public interface IBootcampServicePort {

    Mono<Bootcamp> registerBootcamp(Bootcamp bootcamp);
}
