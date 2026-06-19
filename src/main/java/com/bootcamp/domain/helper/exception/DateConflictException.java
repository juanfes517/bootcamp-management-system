package com.bootcamp.domain.helper.exception;

import com.bootcamp.domain.helper.constant.DomainConstants;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
public class DateConflictException extends RuntimeException {

    private final String statusCode;
    private final Map<String, String> details;

    public DateConflictException(List<Long> conflictIds) {
        super(DomainConstants.DATE_CONFLICT_MESSAGE);
        this.statusCode = DomainConstants.BAD_REQUEST_VALUE;
        this.details = Map.of(DomainConstants.CONFLICT_BOOTCAMPS_STRING, conflictIds.toString());
    }

    public DateConflictException(Map<String, String> details) {
        super(DomainConstants.DATE_CONFLICT_MESSAGE);
        this.statusCode = DomainConstants.BAD_REQUEST_VALUE;
        this.details = details;
    }
}
