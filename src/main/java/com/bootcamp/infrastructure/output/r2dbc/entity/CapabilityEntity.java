package com.bootcamp.infrastructure.output.r2dbc.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("capability")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@ToString
public class CapabilityEntity {

    @Id
    private Long id;
    private String name;
    private String description;
}
