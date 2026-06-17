package com.bootcamp.domain.helper.exception;

import com.bootcamp.domain.helper.constant.DomainConstants;
import lombok.Getter;

import java.util.Map;

@Getter
public class BootcampCountOutOfRangeException extends RuntimeException {
    private final String statusCode;
    private final Map<String, String> details;

    public BootcampCountOutOfRangeException(String message, int submittedBootcamps) {
        super(message);
        this.statusCode = DomainConstants.BAD_REQUEST_VALUE;
        this.details = Map.of(
                DomainConstants.SUBMITTED_BOOTCAMPS_STRING,
                String.valueOf(submittedBootcamps));
    }
}
