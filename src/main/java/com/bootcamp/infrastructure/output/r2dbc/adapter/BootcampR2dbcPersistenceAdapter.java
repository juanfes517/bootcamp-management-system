package com.bootcamp.infrastructure.output.r2dbc.adapter;

import com.bootcamp.domain.model.Bootcamp;
import com.bootcamp.domain.spi.IBootcampPersistencePort;
import com.bootcamp.infrastructure.helper.mapper.BootcampMapper;
import com.bootcamp.infrastructure.output.r2dbc.repository.IBootcampRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class BootcampR2dbcPersistenceAdapter implements IBootcampPersistencePort {

    private final IBootcampRepository bootcampRepository;
    private final BootcampMapper bootcampMapper;

    @Override
    public Mono<Bootcamp> save(Bootcamp bootcamp) {
        return bootcampRepository
                .save(bootcampMapper.toEntity(bootcamp))
                .map(bootcampMapper::toDomain);
    }
}
