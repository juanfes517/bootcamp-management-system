package com.bootcamp.infrastructure.input.rest.dto.request;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class UserBootcampRegistrationRequestDTO {

    private String email;
    private List<Long> bootcampIds;
}
