package com.bootcamp.infrastructure.output.r2dbc.adapter;

import com.bootcamp.domain.model.Technology;
import com.bootcamp.domain.spi.ITechnologyPersistencePort;
import com.bootcamp.infrastructure.helper.mapper.TechnologyMapper;
import com.bootcamp.infrastructure.output.r2dbc.repository.ITechnologyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TechnologyR2dbcPersistenceAdapter implements ITechnologyPersistencePort {

    private final ITechnologyRepository technologyRepository;
    private final TechnologyMapper technologyMapper;

    @Override
    public Mono<Technology> save(Technology technology) {
        return technologyRepository
                .save(technologyMapper.toEntity(technology))
                .map(technologyMapper::toDomain);
    }

    @Override
    public Mono<Boolean> existsByName(String name) {
        return technologyRepository.existsByName(name);
    }

    @Override
    public Flux<Technology> findAllByIds(List<Long> ids) {
        return technologyRepository.findAllById(ids)
                .map(technologyMapper::toDomain);
    }
}
