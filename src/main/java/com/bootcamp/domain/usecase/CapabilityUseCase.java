package com.bootcamp.domain.usecase;

import com.bootcamp.domain.api.ICapabilityServicePort;
import com.bootcamp.domain.helper.constant.DomainConstants;
import com.bootcamp.domain.helper.exception.CapabilityCountOutOfRangeException;
import com.bootcamp.domain.helper.exception.CapabilityNotExistsExceptions;
import com.bootcamp.domain.model.Capability;
import com.bootcamp.domain.model.PageRequest;
import com.bootcamp.domain.spi.ICapabilityPersistencePort;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RequiredArgsConstructor
public class CapabilityUseCase implements ICapabilityServicePort {

    private final ICapabilityPersistencePort capabilityPersistencePort;

    @Override
    public Mono<Capability> registerCapability(Capability capability) {
        return capabilityPersistencePort.save(capability);
    }

    @Override
    public Flux<Capability> getAllCapabilities(PageRequest pageRequest) {
        return capabilityPersistencePort.findAll(pageRequest);
    }

    @Override
    public Mono<List<Capability>> validateBootcampCapabilities(List<Long> capabilityIds) {
        return Mono.just(capabilityIds)
                .flatMap(this::validateCapabilityLimits)
                .flatMap(this::validateCapabilitiesExist);
    }

    private Mono<List<Long>> validateCapabilityLimits(List<Long> capabilityIds) {
        if (capabilityIds.isEmpty() || capabilityIds.size() > 4) {
            return Mono.error(new CapabilityCountOutOfRangeException(
                    DomainConstants.CAPABILITY_LIMITS_MESSAGE,
                    capabilityIds.size()
            ));
        }

        return Mono.just(capabilityIds);
    }

    private Mono<List<Capability>> validateCapabilitiesExist(List<Long> capabilityIds) {
        return capabilityPersistencePort.findAllByIdsWithTechnologies(capabilityIds)
                .collectList()
                .flatMap(existingCapabilities -> {
                    List<Long> existingIds = existingCapabilities.stream()
                            .map(Capability::getId)
                            .toList();

                    List<Long> missingIds = capabilityIds.stream()
                            .filter(id -> !existingIds.contains(id))
                            .toList();

                    if (!missingIds.isEmpty()) {
                        return Mono.error(new CapabilityNotExistsExceptions(missingIds.toString()));
                    }

                    return Mono.just(existingCapabilities);
                });
    }
}
