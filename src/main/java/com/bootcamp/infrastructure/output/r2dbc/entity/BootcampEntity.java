package com.bootcamp.infrastructure.output.r2dbc.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("bootcamp")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@ToString
public class BootcampEntity {

    @Id
    private Long id;
    private String name;
    private String description;
    private LocalDateTime releaseDate;
    private int durationDays;
}
