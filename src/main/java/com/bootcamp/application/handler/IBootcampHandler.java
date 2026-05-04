package com.bootcamp.application.handler;

import com.bootcamp.domain.model.Bootcamp;
import reactor.core.publisher.Mono;

import java.util.List;

public interface IBootcampHandler {

    Mono<Bootcamp> registerBootcamp(Bootcamp bootcamp, List<Long> capabilityIds);
}
