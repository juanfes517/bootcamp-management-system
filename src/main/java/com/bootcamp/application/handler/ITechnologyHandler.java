package com.bootcamp.application.handler;

import com.bootcamp.domain.model.Technology;
import reactor.core.publisher.Mono;

public interface ITechnologyHandler {

    Mono<Technology> registerTechnology(Technology technology);
}
