package com.bootcamp.domain.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Technology {

    private Long id;
    private String name;
    private String description;
}
