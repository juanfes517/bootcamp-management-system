package com.bootcamp.infrastructure.input.rest.controlleradvice;

import com.bootcamp.domain.helper.exception.DuplicateTechnologyException;
import com.bootcamp.infrastructure.input.rest.dto.response.ExceptionResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.validation.FieldError;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateTechnologyException.class)
    public ResponseEntity<ExceptionResponseDTO> handlerDuplicateTechnologyException(DuplicateTechnologyException e) {
        ExceptionResponseDTO response = ExceptionResponseDTO.builder()
                .message(e.getMessage())
                .statusCode(e.getStatusCode())
                .details(e.getDetails())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity<ExceptionResponseDTO> handleValidationErrors(WebExchangeBindException ex) {
        Map<String, String> details = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        error -> Optional.ofNullable(error.getDefaultMessage()).orElse("Invalid value")
                ));

        ExceptionResponseDTO response = ExceptionResponseDTO.builder()
                .message("Validation error")
                .statusCode(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                .details(details)
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }
}
