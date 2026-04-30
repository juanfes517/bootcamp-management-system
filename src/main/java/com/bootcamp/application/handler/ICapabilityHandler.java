package com.bootcamp.application.handler;

import com.bootcamp.domain.model.Capability;
import com.bootcamp.domain.model.PageRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ICapabilityHandler {

    Mono<Capability> registerCapability(Capability capability, List<Long> technologiesIds);
    Flux<Capability> getAllCapabilities(PageRequest pageRequest);
}
