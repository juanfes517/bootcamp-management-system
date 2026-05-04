package com.bootcamp.domain.helper.exception;

import com.bootcamp.domain.helper.constant.DomainConstants;
import lombok.Getter;

import java.util.Map;

@Getter
public class CapabilityNotExistsExceptions extends RuntimeException {

    private final String statusCode;
    private final Map<String, String> details;

    public CapabilityNotExistsExceptions(String message, String missingIds) {
        super(message);
        this.statusCode = DomainConstants.BAD_REQUEST_VALUE;
        this.details = Map.of(
                DomainConstants.SUBMITTED_CAPABILITIES_STRING,
                missingIds);
    }

    public CapabilityNotExistsExceptions(String missingIds) {
        super(DomainConstants.CAPABILITIES_NOT_EXISTS_MESSAGE);
        this.statusCode = DomainConstants.BAD_REQUEST_VALUE;
        this.details = Map.of(
                DomainConstants.SUBMITTED_CAPABILITIES_STRING,
                missingIds);
    }
}
