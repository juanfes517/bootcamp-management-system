package com.bootcamp.domain.api;

import reactor.core.publisher.Mono;

import java.util.List;

public interface ICapabilityBootcampServicePort {

    Mono<Void> createCapabilityBootcamp(Long bootcampId, List<Long> capabilityIds);
}
