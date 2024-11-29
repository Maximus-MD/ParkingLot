package com.endava.md.internship.parkinglot.validation;

import com.endava.md.internship.parkinglot.validation.validator.AccessibleParkingLevelValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class CustomAccessibleParkingLevelValidatorTest {

    private AccessibleParkingLevelValidator accessibleParkingLevelValidator;
    private Map<String, Integer> parkingLevels;

    @BeforeEach
    void setUp() {
        accessibleParkingLevelValidator = new AccessibleParkingLevelValidator();
        parkingLevels = new HashMap<>();
    }

    private static Stream<Arguments> provideParkingLevels() {
        return Stream.of(
                Arguments.of("B", 200),
                Arguments.of("B", 0),
                Arguments.of("H", 1)
        );
    }

    @Test
    void isValid_CorrectParkingLevel_ReturnsTrue() {
        parkingLevels.put("A", 1);

        boolean isValid = accessibleParkingLevelValidator.isValid(parkingLevels, null);

        assertTrue(isValid);
    }

    @ParameterizedTest
    @MethodSource("provideParkingLevels")
    void isValid_IncorrectParkingLevel_ReturnsFalse(String level, int place) {
        parkingLevels.put(level, place);

        boolean isValid = accessibleParkingLevelValidator.isValid(parkingLevels, null);

        assertFalse(isValid);
    }

    @Test
    void isValid_CorrectParkingLevelPlace_ReturnsTrue() {
        parkingLevels.put("B", 100);

        boolean isValid = accessibleParkingLevelValidator.isValid(parkingLevels, null);

        assertTrue(isValid);
    }

}