package com.endava.md.internship.parkinglot.validation.validator;

import com.endava.md.internship.parkinglot.model.ParkingSpotType;
import com.endava.md.internship.parkinglot.validation.annotation.CorrectParkingSpotType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class CorrectParkingSpotTypeValidator implements ConstraintValidator<CorrectParkingSpotType, String> {

    @Override
    public boolean isValid(String parkingSpotType, ConstraintValidatorContext constraintValidatorContext) {
        return Arrays.stream(ParkingSpotType.values()).map(Enum::name).toList().contains(parkingSpotType.toUpperCase());
    }
}
