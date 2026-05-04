package com.bootcamp.domain.spi;

import reactor.core.publisher.Mono;

import java.util.List;

public interface ICapabilityBootcampPersistencePort {

    Mono<Void> save(Long bootcampId, List<Long> capabilityIds);
}
