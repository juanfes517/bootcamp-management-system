package com.bootcamp.infrastructure.input.rest.dto.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@ToString
public class TechnologyResponseDTO {

    private Long id;
    private String name;
    private String description;
}
