package com.endava.md.internship.parkinglot.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.OK)
public class RegistrationException extends RuntimeException {
    private final int errorCode;

    public RegistrationException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}

