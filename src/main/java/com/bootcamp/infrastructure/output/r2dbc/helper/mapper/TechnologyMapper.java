package com.bootcamp.infrastructure.output.r2dbc.helper.mapper;

import com.bootcamp.domain.model.Technology;
import com.bootcamp.infrastructure.output.r2dbc.entity.TechnologyEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TechnologyMapper {

    Technology toDomain(TechnologyEntity technologyEntity);
    TechnologyEntity toEntity(Technology technology);
}
