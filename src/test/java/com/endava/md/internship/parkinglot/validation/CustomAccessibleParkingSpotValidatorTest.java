package com.endava.md.internship.parkinglot.validation;

import com.endava.md.internship.parkinglot.validation.validator.AccessibleParkingSpotValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
class CustomAccessibleParkingSpotValidatorTest {

    private AccessibleParkingSpotValidator accessibleParkingSpotValidator;
    private Map<String, Integer> parkingLevels;

    @BeforeEach
    void setUp() {
        accessibleParkingSpotValidator = new AccessibleParkingSpotValidator();
        parkingLevels = new HashMap<>();
    }

    @Test
    void isValid_CorrectParkingPlace_ReturnsTrue() {
        parkingLevels.put("A", 130);

        boolean isValid = accessibleParkingSpotValidator.isValid(parkingLevels, null);

        assertThat(isValid).isTrue();
    }

    @Test
    void isValid_ParkingPlaceMoreThatAvailable_ReturnsFalse() {
        parkingLevels.put("B", 151);

        boolean isValid = accessibleParkingSpotValidator.isValid(parkingLevels, null);

        assertThat(isValid).isFalse();
    }

    @Test
    void isValid_ParkingPlaceLessThatAvailable_ReturnsFalse() {
        parkingLevels.put("B", 0);

        boolean isValid = accessibleParkingSpotValidator.isValid(parkingLevels, null);

        assertThat(isValid).isFalse();
    }
}