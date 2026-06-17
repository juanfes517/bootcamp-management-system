package com.bootcamp.domain.helper.exception;

import com.bootcamp.domain.helper.constant.DomainConstants;
import lombok.Getter;

import java.util.Map;

@Getter
public class BootcampNotExistsException extends RuntimeException {

    private final String statusCode;
    private final Long missingId;

    public BootcampNotExistsException(Long missingId) {
        super(DomainConstants.BOOTCAMP_NOT_EXIST_MESSAGE);
        this.statusCode = DomainConstants.BAD_REQUEST_VALUE;
        this.missingId = missingId;
    }
}
