package com.endava.md.internship.parkinglot.validation.validator;

import com.endava.md.internship.parkinglot.validation.annotation.AccessibleParkingLevel;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class AccessibleParkingLevelValidator implements ConstraintValidator<AccessibleParkingLevel, Map<String, Integer>> {

    private static final List<String> ACCESSIBLE_PARKING_LEVELS = new ArrayList<>(Arrays.asList("A", "B", "C", "D", "E"));

    @Override
    public boolean isValid(Map<String, Integer> parkingLevels, ConstraintValidatorContext constraintValidatorContext) {
        return new HashSet<>(ACCESSIBLE_PARKING_LEVELS).containsAll(parkingLevels.keySet());
    }
}
