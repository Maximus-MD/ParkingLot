package com.endava.md.internship.parkinglot.validation.validator;

import com.endava.md.internship.parkinglot.validation.annotation.AccessibleParkingLevel;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class AccessibleParkingLevelValidator implements ConstraintValidator<AccessibleParkingLevel, Map<String, Integer>> {

    private static final List<String> ACCESSIBLE_PARKING_LEVELS = new ArrayList<>(Arrays.asList("A", "B", "C", "D", "E"));

    @Override
    public boolean isValid(Map<String, Integer> parkingLevels, ConstraintValidatorContext constraintValidatorContext) {

        for (String key : parkingLevels.keySet()) {
            if (!ACCESSIBLE_PARKING_LEVELS.contains(key)) {
                return false;
            }
        }

        for (Integer value : parkingLevels.values()) {
            if (value < 1 || value > 150) {
                return false;
            }
        }

        return true;
    }
}
