package com.bootcamp.domain.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Bootcamp {

    private Long id;
    private String name;
    private String description;
    private LocalDateTime releaseDate;
    private int durationDays;
    private List<Capability> capabilityList;
}
