package com.endava.md.internship.parkinglot.exception;

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

