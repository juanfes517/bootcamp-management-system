package com.bootcamp.domain.api;

import reactor.core.publisher.Mono;

import java.util.List;

public interface ITechnologyCapabilityServicePort {

    Mono<Void> createTechnologyCapability(Long capabilityId, List<Long> technologiesIds);
}
