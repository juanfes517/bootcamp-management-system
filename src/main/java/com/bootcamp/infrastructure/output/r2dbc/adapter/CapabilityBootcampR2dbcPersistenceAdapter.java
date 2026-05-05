package com.bootcamp.infrastructure.output.r2dbc.adapter;

import com.bootcamp.domain.spi.ICapabilityBootcampPersistencePort;
import com.bootcamp.infrastructure.output.r2dbc.entity.CapabilityBootcampEntity;
import com.bootcamp.infrastructure.output.r2dbc.repository.ICapabilityBootcampRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CapabilityBootcampR2dbcPersistenceAdapter implements ICapabilityBootcampPersistencePort {

    private final ICapabilityBootcampRepository capabilityBootcampRepository;

    @Override
    public Mono<Void> save(Long bootcampId, List<Long> capabilityIds) {
        List<CapabilityBootcampEntity> capabilityBootcampEntities = capabilityIds.stream()
                .map(capabilityId -> CapabilityBootcampEntity.builder()
                        .bootcampId(bootcampId)
                        .capabilityId(capabilityId)
                        .build())
                .toList();

        return capabilityBootcampRepository
                .saveAll(capabilityBootcampEntities)
                .then();
    }
}
