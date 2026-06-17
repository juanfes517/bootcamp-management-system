package com.bootcamp.domain.helper.exception;

import com.bootcamp.domain.helper.constant.DomainConstants;
import lombok.Getter;

import java.util.Map;

@Getter
public class UserNotExistsException extends RuntimeException {

    private final String statusCode;
    private final Map<String, String> details;

    public UserNotExistsException(String email) {
        super(DomainConstants.USER_NOT_EXISTS_MESSAGE);
        this.statusCode = DomainConstants.NOT_FOUND_VALUE;
        this.details = Map.of(
                DomainConstants.EMAIL_STRING,
                email);
    }
}
