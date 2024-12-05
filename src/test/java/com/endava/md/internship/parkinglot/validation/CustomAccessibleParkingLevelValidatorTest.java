package com.endava.md.internship.parkinglot.validation;

import com.endava.md.internship.parkinglot.validation.validator.AccessibleParkingLevelValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
class CustomAccessibleParkingLevelValidatorTest {

    private AccessibleParkingLevelValidator accessibleParkingLevelValidator;
    private Map<String, Integer> parkingLevels;

    @BeforeEach
    void setUp() {
        accessibleParkingLevelValidator = new AccessibleParkingLevelValidator();
        parkingLevels = new HashMap<>();
    }

    @Test
    void isValid_CorrectParkingLevel_ReturnsTrue() {
        parkingLevels.put("A", 1);

        boolean isValid = accessibleParkingLevelValidator.isValid(parkingLevels, null);

        assertThat(isValid).isTrue();
    }

    @Test
    void isValid_IncorrectParkingLevel_ReturnsFalse() {
        parkingLevels.put("W", 12);

        boolean isValid = accessibleParkingLevelValidator.isValid(parkingLevels, null);

        assertThat(isValid).isFalse();
    }

}