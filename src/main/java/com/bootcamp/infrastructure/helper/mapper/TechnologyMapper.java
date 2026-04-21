package com.bootcamp.infrastructure.helper.mapper;

import com.bootcamp.domain.model.Technology;
import com.bootcamp.infrastructure.input.rest.dto.request.TechnologyRequestDTO;
import com.bootcamp.infrastructure.input.rest.dto.response.TechnologyResponseDTO;
import com.bootcamp.infrastructure.output.r2dbc.entity.TechnologyEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TechnologyMapper {

    Technology toDomain(TechnologyEntity technologyEntity);
    Technology toDomain(TechnologyRequestDTO technologyRequestDTO);
    TechnologyEntity toEntity(Technology technology);
    TechnologyResponseDTO toResponseDTO(Technology technology);
}
