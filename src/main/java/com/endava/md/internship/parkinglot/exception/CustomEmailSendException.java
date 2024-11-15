package com.endava.md.internship.parkinglot.exception;

public class CustomEmailSendException extends RuntimeException {
    public CustomEmailSendException(String message) {
        super(message);
    }
}
