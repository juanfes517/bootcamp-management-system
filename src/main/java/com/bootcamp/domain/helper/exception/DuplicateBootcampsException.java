package com.bootcamp.domain.helper.exception;

import com.bootcamp.domain.helper.constant.DomainConstants;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
public class DuplicateBootcampsException extends RuntimeException {
    private final String statusCode;
    private final Map<String, String> details;

    public DuplicateBootcampsException(String message, List<Long> duplicates) {
        super(message);
        this.statusCode = DomainConstants.CONFLICT_VALUE;
        this.details = Map.of(
                DomainConstants.DUPLICATE_VALUES_STRING,
                String.valueOf(duplicates));
    }

    public DuplicateBootcampsException(String message, Map<String, String> details) {
        super(message);
        this.statusCode = DomainConstants.CONFLICT_VALUE;
        this.details = details;
    }
}
