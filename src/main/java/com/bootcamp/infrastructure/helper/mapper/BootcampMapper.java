package com.bootcamp.infrastructure.helper.mapper;

import com.bootcamp.domain.model.Bootcamp;
import com.bootcamp.infrastructure.input.rest.dto.request.BootcampRequestDTO;
import com.bootcamp.infrastructure.input.rest.dto.response.BootcampResponseDTO;
import com.bootcamp.infrastructure.output.r2dbc.entity.BootcampEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BootcampMapper {

    @Mapping(target = "capabilityList", ignore = true)
    Bootcamp toDomain(BootcampEntity bootcampEntity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "capabilityList", ignore = true)
    Bootcamp toDomain(BootcampRequestDTO bootcampRequestDTO);

    BootcampEntity toEntity(Bootcamp bootcamp);

    BootcampResponseDTO toResponse(Bootcamp bootcamp);
}
