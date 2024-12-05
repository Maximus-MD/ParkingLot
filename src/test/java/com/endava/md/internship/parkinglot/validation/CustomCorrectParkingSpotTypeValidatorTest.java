package com.endava.md.internship.parkinglot.validation;

import com.endava.md.internship.parkinglot.validation.validator.CorrectParkingSpotTypeValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@ExtendWith(MockitoExtension.class)
class CustomCorrectParkingSpotTypeValidatorTest {

    private CorrectParkingSpotTypeValidator correctParkingSpotTypeValidator;

    @BeforeEach
    public void setUp() {
        correctParkingSpotTypeValidator = new CorrectParkingSpotTypeValidator();
    }

    @Test
    void isValid_CorrectParkingSpotType(){
        String correctParkingSpotType = "REGULAR";

        boolean isValid = correctParkingSpotTypeValidator.isValid(correctParkingSpotType,null);

        assertThat(isValid).isTrue();
    }

    @Test
    void isValid_IncorrectParkingSpotType(){
        String correctParkingSpotType = "VIP";

        boolean isValid = correctParkingSpotTypeValidator.isValid(correctParkingSpotType,null);

        assertThat(isValid).isFalse();
    }
}
