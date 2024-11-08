package com.endava.md.internship.parkinglot.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.OK)
public class RegistrationException extends RuntimeException {
    private final int errorCode;

    public RegistrationException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {

        return errorCode;
    }
}

