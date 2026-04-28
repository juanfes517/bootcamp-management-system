package com.bootcamp.application.handler.impl;

import com.bootcamp.application.handler.ICapabilityHandler;
import com.bootcamp.domain.api.ICapabilityServicePort;
import com.bootcamp.domain.api.ITechnologyCapabilityServicePort;
import com.bootcamp.domain.api.ITechnologyServicePort;
import com.bootcamp.domain.model.Capability;
import com.bootcamp.domain.model.PageRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CapabilityHandler implements ICapabilityHandler {

    private final ICapabilityServicePort capabilityServicePort;
    private final ITechnologyServicePort technologyServicePort;
    private final ITechnologyCapabilityServicePort technologyCapabilityServicePort;

    @Override
    public Mono<Capability> registerCapability(Capability capability, List<Long> technologiesIds) {
        return technologyServicePort
                .validateCapabilityTechnologies(technologiesIds)
                .flatMap(validTechnologies -> capabilityServicePort
                        .registerCapability(capability)
                        .flatMap(savedCapability -> technologyCapabilityServicePort
                                .createTechnologyCapability(savedCapability.getId(), technologiesIds)
                                .thenReturn(savedCapability))
                        .doOnNext(savedCapability -> savedCapability.setTechnologyList(validTechnologies))
                );
    }

    @Override
    public Flux<Capability> getAllCapabilities(PageRequest pageRequest) {
        return capabilityServicePort.getAllCapabilities(pageRequest);
    }
}
