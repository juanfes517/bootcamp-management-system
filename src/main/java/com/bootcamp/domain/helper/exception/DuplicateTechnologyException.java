package com.bootcamp.domain.helper.exception;

import com.bootcamp.domain.helper.constant.DomainConstants;
import lombok.Getter;

import java.util.Map;

@Getter
public class DuplicateTechnologyException extends RuntimeException {

    private final String statusCode;
    private final Map<String, String> details;

    public DuplicateTechnologyException(String name) {
        super(DomainConstants.DUPLICATE_TECHNOLOGY_MESSAGE);
        this.statusCode = DomainConstants.BAD_REQUEST_VALUE;
        this.details = Map.of(DomainConstants.NAME_STRING, name);
    }

    public DuplicateTechnologyException(String message, String names) {
        super(message);
        this.statusCode = DomainConstants.BAD_REQUEST_VALUE;
        this.details = Map.of(DomainConstants.IDS_STRING, names);
    }
}
