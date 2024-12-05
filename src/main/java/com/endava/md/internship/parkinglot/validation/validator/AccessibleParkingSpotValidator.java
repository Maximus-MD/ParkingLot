package com.endava.md.internship.parkinglot.validation.validator;

import com.endava.md.internship.parkinglot.validation.annotation.AccessibleParkingSpot;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Map;

public class AccessibleParkingSpotValidator implements ConstraintValidator<AccessibleParkingSpot, Map<String, Integer>> {

    @Override
    public boolean isValid(Map<String, Integer> parkingLevels, ConstraintValidatorContext constraintValidatorContext) {
        return parkingLevels.values().stream().allMatch(value -> value >= 1 && value <= 150);
    }
}
