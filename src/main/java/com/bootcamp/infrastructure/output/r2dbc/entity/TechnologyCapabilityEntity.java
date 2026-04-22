package com.bootcamp.infrastructure.output.r2dbc.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("technology_capability")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@ToString
public class TechnologyCapabilityEntity {

    @Id
    private Long id;
    private Long technologyId;
    private Long capabilityId;
}
