package com.bootcamp.application.handler;

import com.bootcamp.domain.model.Capability;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ICapabilityHandler {

    Mono<Capability> registerCapability(Capability capability, List<Long> technologiesIds);
}
