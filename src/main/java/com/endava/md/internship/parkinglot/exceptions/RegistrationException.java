package com.endava.md.internship.parkinglot.exceptions;

public class RegistrationException extends RuntimeException {
    private final int errorCode;
    //private final String userMessage;

    public RegistrationException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;

    }

    public int getErrorCode() {
        return errorCode;
    }
}
