package com.bootcamp.infrastructure.input.rest.dto.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class RegistrationResultResponseDTO {

    private Object bootcamp;
    private boolean success;
    private String errorMessage;
}
