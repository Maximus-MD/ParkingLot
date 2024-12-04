package com.endava.md.internship.parkinglot.validation.validator;

import com.endava.md.internship.parkinglot.model.ParkingSpotType;
import com.endava.md.internship.parkinglot.validation.annotation.CorrectParkingSpotType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CorrectParkingSpotTypeValidator implements ConstraintValidator<CorrectParkingSpotType, String> {

    @Override
    public boolean isValid(String parkingSpotType, ConstraintValidatorContext constraintValidatorContext) {
            List<String> parkingSpotTypes = new ArrayList<>();
            Arrays.stream(ParkingSpotType.values()).toList().forEach(type-> parkingSpotTypes.add(type.name()));

            return parkingSpotTypes.contains(parkingSpotType.toUpperCase());
    }
}
