package com.bootcamp.infrastructure.output.r2dbc.adapter;

import com.bootcamp.domain.spi.ITechnologyCapabilityPersistencePort;
import com.bootcamp.infrastructure.output.r2dbc.entity.TechnologyCapabilityEntity;
import com.bootcamp.infrastructure.output.r2dbc.repository.ITechnologyCapabilityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TechnologyCapabilityR2dbcPersistenceAdapter implements ITechnologyCapabilityPersistencePort {

    private final ITechnologyCapabilityRepository technologyCapabilityRepository;

    @Override
    public Mono<Void> save(Long capabilityId, List<Long> technologiesIds) {
        return Flux.fromIterable(technologiesIds)
                .map(technologyId -> TechnologyCapabilityEntity.builder()
                        .capabilityId(capabilityId)
                        .technologyId(technologyId)
                        .build())
                .flatMap(technologyCapabilityRepository::save)
                .then();
    }
}
