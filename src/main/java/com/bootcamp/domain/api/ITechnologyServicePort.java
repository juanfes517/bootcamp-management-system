package com.bootcamp.domain.api;

import com.bootcamp.domain.model.Technology;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ITechnologyServicePort {

    Mono<Technology> registerTechnology(Technology technology);
    Mono<List<Technology>> validateCapabilityTechnologies(List<Long> technologies);
}
