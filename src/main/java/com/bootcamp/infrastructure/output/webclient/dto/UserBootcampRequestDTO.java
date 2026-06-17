package com.bootcamp.infrastructure.output.webclient.dto;

import com.bootcamp.domain.model.Bootcamp;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class UserBootcampRequestDTO {

    private String email;
    private List<Bootcamp> bootcamps;
}
