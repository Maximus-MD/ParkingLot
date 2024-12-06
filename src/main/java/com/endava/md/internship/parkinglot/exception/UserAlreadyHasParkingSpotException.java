package com.endava.md.internship.parkinglot.exception;

public class UserAlreadyHasParkingSpotException extends RuntimeException {
    public UserAlreadyHasParkingSpotException(String message) {
        super(message);
    }
}
