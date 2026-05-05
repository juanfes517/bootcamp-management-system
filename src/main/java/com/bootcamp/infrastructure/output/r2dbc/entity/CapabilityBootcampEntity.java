package com.bootcamp.infrastructure.output.r2dbc.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("capability_bootcamp")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@ToString
public class CapabilityBootcampEntity {

    @Id
    private Long id;
    private Long bootcampId;
    private Long capabilityId;
}
