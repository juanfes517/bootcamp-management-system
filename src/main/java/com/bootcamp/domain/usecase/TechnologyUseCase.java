package com.bootcamp.domain.usecase;

import com.bootcamp.domain.api.ITechnologyServicePort;
import com.bootcamp.domain.helper.exception.DuplicateTechnologyException;
import com.bootcamp.domain.model.Technology;
import com.bootcamp.domain.spi.ITechnologyPersistencePort;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class TechnologyUseCase implements ITechnologyServicePort {

    private final ITechnologyPersistencePort technologyPersistencePort;

    public Mono<Technology> registerTechnology(Technology technology) {
        return technologyPersistencePort
                .existsByName(technology.getName())
                .filter(Boolean.FALSE::equals)
                .switchIfEmpty(Mono.error(new DuplicateTechnologyException(technology.getName())))
                .flatMap(exists -> technologyPersistencePort.save(technology));
    }


}
