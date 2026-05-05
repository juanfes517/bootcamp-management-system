package com.bootcamp.application.handler.impl;

import com.bootcamp.application.handler.IBootcampHandler;
import com.bootcamp.domain.api.IBootcampServicePort;
import com.bootcamp.domain.api.ICapabilityBootcampServicePort;
import com.bootcamp.domain.api.ICapabilityServicePort;
import com.bootcamp.domain.model.Bootcamp;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BootcampHandler implements IBootcampHandler {

    private final IBootcampServicePort bootcampService;
    private final ICapabilityBootcampServicePort capabilityBootcampService;
    private final ICapabilityServicePort capabilityService;

    @Override
    public Mono<Bootcamp> registerBootcamp(Bootcamp bootcamp, List<Long> capabilityIds) {
        return capabilityService
                .validateBootcampCapabilities(capabilityIds)
                .flatMap(validCapabilities -> bootcampService
                        .registerBootcamp(bootcamp)
                        .flatMap(savedBootcamp -> capabilityBootcampService
                                .createCapabilityBootcamp(savedBootcamp.getId(), capabilityIds)
                                .thenReturn(savedBootcamp))
                        .doOnNext(savedBootcamp -> savedBootcamp.setCapabilityList(validCapabilities)));
    }

}
