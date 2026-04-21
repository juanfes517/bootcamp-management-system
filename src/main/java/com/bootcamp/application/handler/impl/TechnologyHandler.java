package com.bootcamp.application.handler.impl;

import com.bootcamp.application.handler.ITechnologyHandler;
import com.bootcamp.domain.api.ITechnologyServicePort;
import com.bootcamp.domain.model.Technology;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class TechnologyHandler implements ITechnologyHandler {

    private final ITechnologyServicePort technologyServicePort;

    @Override
    public Mono<Technology> registerTechnology(Technology technology) {
        return technologyServicePort.registerTechnology(technology);
    }
}
