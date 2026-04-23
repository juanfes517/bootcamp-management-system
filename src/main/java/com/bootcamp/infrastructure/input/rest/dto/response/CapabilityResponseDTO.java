package com.bootcamp.infrastructure.input.rest.dto.response;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class CapabilityResponseDTO {

    private Long id;
    private String name;
    private String description;
    private List<TechnologyResponseDTO> technologyList;
}
