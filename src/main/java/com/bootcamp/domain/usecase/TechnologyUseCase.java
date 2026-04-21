package com.bootcamp.domain.usecase;

import com.bootcamp.domain.api.ITechnologyServicePort;
import com.bootcamp.domain.model.Technology;
import com.bootcamp.domain.spi.ITechnologyPersistencePort;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class TechnologyUseCase implements ITechnologyServicePort {

    private final ITechnologyPersistencePort technologyPersistencePort;

    @Override
    public Mono<Technology> registerTecnology(Technology technology) {
        return technologyPersistencePort.save(technology);
    }


}
