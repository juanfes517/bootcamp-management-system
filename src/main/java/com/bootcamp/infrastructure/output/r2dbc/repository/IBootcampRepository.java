package com.bootcamp.infrastructure.output.r2dbc.repository;

import com.bootcamp.infrastructure.output.r2dbc.entity.BootcampEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface IBootcampRepository extends R2dbcRepository<BootcampEntity, Long> {
}
