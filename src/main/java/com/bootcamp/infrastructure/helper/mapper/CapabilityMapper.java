package com.bootcamp.infrastructure.helper.mapper;

import com.bootcamp.domain.model.Capability;
import com.bootcamp.infrastructure.input.rest.dto.request.CapabilityRequestDTO;
import com.bootcamp.infrastructure.input.rest.dto.response.CapabilityResponseDTO;
import com.bootcamp.infrastructure.output.r2dbc.entity.CapabilityEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CapabilityMapper {

    @Mapping(target = "technologyList", ignore = true)
    Capability toDomain(CapabilityEntity capabilityEntity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "technologyList", ignore = true)
    Capability toDomain(CapabilityRequestDTO capabilityRequestDTO);
    CapabilityEntity toEntity(Capability capability);
    CapabilityResponseDTO toResponseDTO(Capability capability);
}
