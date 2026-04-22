package com.bootcamp.domain.api;

import com.bootcamp.domain.model.Capability;
import reactor.core.publisher.Mono;

public interface ICapabilityServicePort {

    Mono<Capability> registerCapability(Capability capability);
}
