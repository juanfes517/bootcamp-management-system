package com.bootcamp.domain.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class RegistrationResult {

    private Object bootcamp;
    private boolean success;
    private String errorMessage;
}
