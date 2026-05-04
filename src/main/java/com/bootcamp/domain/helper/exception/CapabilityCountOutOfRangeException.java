package com.bootcamp.domain.helper.exception;

import com.bootcamp.domain.helper.constant.DomainConstants;
import lombok.Getter;

import java.util.Map;

@Getter
public class CapabilityCountOutOfRangeException extends RuntimeException {
    private final String statusCode;
    private final Map<String, String> details;

    public CapabilityCountOutOfRangeException(String message, int submittedCapabilities) {
        super(message);
        this.statusCode = DomainConstants.BAD_REQUEST_VALUE;
        this.details = Map.of(
                DomainConstants.SUBMITTED_CAPABILITIES_STRING,
                String.valueOf(submittedCapabilities));
    }
}
