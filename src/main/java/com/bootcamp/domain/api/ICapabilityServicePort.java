package com.bootcamp.domain.api;

import com.bootcamp.domain.model.Capability;
import com.bootcamp.domain.model.PageRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ICapabilityServicePort {

    Mono<Capability> registerCapability(Capability capability);
    Flux<Capability> getAllCapabilities(PageRequest pageRequest);
}
