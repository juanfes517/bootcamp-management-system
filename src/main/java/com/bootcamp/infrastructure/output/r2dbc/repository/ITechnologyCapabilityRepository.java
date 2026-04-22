package com.bootcamp.infrastructure.output.r2dbc.repository;

import com.bootcamp.infrastructure.output.r2dbc.entity.TechnologyCapabilityEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface ITechnologyCapabilityRepository extends R2dbcRepository<TechnologyCapabilityEntity, Long> {
}
