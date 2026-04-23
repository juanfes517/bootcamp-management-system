package com.bootcamp.infrastructure.output.r2dbc.repository;

import com.bootcamp.infrastructure.output.r2dbc.entity.CapabilityEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface ICapabilityRepository extends R2dbcRepository<CapabilityEntity, Long> {
}
