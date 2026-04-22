package com.bootcamp.domain.spi;

import reactor.core.publisher.Mono;

import java.util.List;

public interface ITechnologyCapabilityPersistencePort {

    Mono<Void> save(Long capabilityId, List<Long> technologiesIds);
}
