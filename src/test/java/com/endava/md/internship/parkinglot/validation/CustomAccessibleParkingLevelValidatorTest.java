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

    @ParameterizedTest
    @MethodSource("provideParkingLevels")
    void isValid_IncorrectParkingLevel_ReturnsFalse(String level, int place) {
        parkingLevels.put(level, place);

        boolean isValid = accessibleParkingLevelValidator.isValid(parkingLevels, null);

        assertThat(isValid).isFalse();
    }

    @Test
    void isValid_CorrectParkingLevelPlace_ReturnsTrue() {
        parkingLevels.put("B", 100);

        boolean isValid = accessibleParkingLevelValidator.isValid(parkingLevels, null);

        assertThat(isValid).isTrue();
    }

    private static Stream<Arguments> provideParkingLevels() {
        return Stream.of(
                Arguments.of("B", 200),
                Arguments.of("B", 0),
                Arguments.of("H", 1)
        );
    }
}