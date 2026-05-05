package com.bootcamp.domain.api;

import com.bootcamp.domain.model.Capability;
import com.bootcamp.domain.model.PageRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ICapabilityServicePort {

    Mono<Capability> registerCapability(Capability capability);
    Flux<Capability> getAllCapabilities(PageRequest pageRequest);
    Mono<List<Capability>> validateBootcampCapabilities(List<Long> capabilityIds);
}
