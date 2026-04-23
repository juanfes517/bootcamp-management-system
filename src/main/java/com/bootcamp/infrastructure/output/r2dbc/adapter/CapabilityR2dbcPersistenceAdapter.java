package com.bootcamp.infrastructure.output.r2dbc.adapter;

import com.bootcamp.domain.model.Capability;
import com.bootcamp.domain.spi.ICapabilityPersistencePort;
import com.bootcamp.infrastructure.helper.mapper.CapabilityMapper;
import com.bootcamp.infrastructure.output.r2dbc.repository.ICapabilityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class CapabilityR2dbcPersistenceAdapter implements ICapabilityPersistencePort {

    private final ICapabilityRepository capabilityRepository;
    private final CapabilityMapper capabilityMapper;

    @Override
    public Mono<Capability> save(Capability capability) {
        return capabilityRepository
                .save(capabilityMapper.toEntity(capability))
                .map(capabilityMapper::toDomain);
    }
}
