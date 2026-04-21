package com.bootcamp.infrastructure.input.rest.dto.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@ToString
public class ExceptionResponseDTO {

    private String message;
    private String statusCode;
    private Map<String, String> details;
    private LocalDateTime timestamp;
}
