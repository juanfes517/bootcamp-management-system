package com.bootcamp.domain.spi;

import com.bootcamp.domain.model.Capability;
import com.bootcamp.domain.model.PageRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ICapabilityPersistencePort {

    Mono<Capability> save(Capability capability);
    Flux<Capability> findAll(PageRequest pageRequest);
    Flux<Capability> findAllByIdsWithTechnologies(List<Long> capabilitiesIds);
}
