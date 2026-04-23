package com.bootcamp.domain.model;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Capability {

    private Long id;
    private String name;
    private String description;
    private List<Technology> technologyList;
}
