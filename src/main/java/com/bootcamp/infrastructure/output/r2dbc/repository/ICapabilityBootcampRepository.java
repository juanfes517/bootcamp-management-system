package com.bootcamp.infrastructure.output.r2dbc.repository;

import com.bootcamp.infrastructure.output.r2dbc.entity.CapabilityBootcampEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface ICapabilityBootcampRepository extends R2dbcRepository<CapabilityBootcampEntity, Long> {
}
