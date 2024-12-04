package com.endava.md.internship.parkinglot.exception;

public class OccupiedParkingSpotException extends RuntimeException {
    public OccupiedParkingSpotException(String message) {
        super(message);
    }
}
