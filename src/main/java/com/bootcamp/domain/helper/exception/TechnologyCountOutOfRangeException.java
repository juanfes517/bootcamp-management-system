package com.bootcamp.domain.helper.exception;

import com.bootcamp.domain.helper.constant.DomainConstants;
import lombok.Getter;

import java.util.Map;

@Getter
public class TechnologyCountOutOfRangeException extends RuntimeException {

    private final String statusCode;
    private final Map<String, String> details;

    public TechnologyCountOutOfRangeException(String message, int submittedTechnologies) {
        super(message);
        this.statusCode = DomainConstants.BAD_REQUEST_VALUE;
        this.details = Map.of(
                DomainConstants.SUBMITTED_TECHNOLOGIES_STRING,
                String.valueOf(submittedTechnologies));
    }
}
