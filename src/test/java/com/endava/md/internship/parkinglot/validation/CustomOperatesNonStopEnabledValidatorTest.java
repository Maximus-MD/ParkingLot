package com.endava.md.internship.parkinglot.validation;

import com.endava.md.internship.parkinglot.dto.ParkingLotRequestDto;
import com.endava.md.internship.parkinglot.utils.ParkingLotDTOUtils;
import com.endava.md.internship.parkinglot.validation.validator.OperatesNonStopEnabledValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class CustomOperatesNonStopEnabledValidatorTest {

    private OperatesNonStopEnabledValidator operatesNonStopEnabledValidator;

    @BeforeEach
    void setUp() {
        operatesNonStopEnabledValidator = new OperatesNonStopEnabledValidator();
    }

    @Test
    void isValid_OperatesNonStopIsTrue_ReturnsTrue() {
        ParkingLotRequestDto parkingLotRequestDto = ParkingLotDTOUtils.getPreparedParkingLotRequestDtoWithOperatesNonStopTrue();

        boolean operatesNonStopIsTrue = operatesNonStopEnabledValidator.isValid(parkingLotRequestDto, null);

        assertTrue(operatesNonStopIsTrue);
    }

    @Test
    void isValid_OperatesNonStopIsFalseAndTimeNull_ReturnsFalse() {
        ParkingLotRequestDto parkingLotRequestDto = ParkingLotDTOUtils.getPreparedParkingLotRequestDtoWithNullTimeAndNonStopFalse();

        boolean operatesNonStopIsTrue = operatesNonStopEnabledValidator.isValid(parkingLotRequestDto, null);

        assertFalse(operatesNonStopIsTrue);
    }

    @Test
    void isValid_OperatesNonStopIsFalseAndTimeIsValid_ReturnsTrue() {
        ParkingLotRequestDto parkingLotRequestDto = ParkingLotDTOUtils.getPreparedParkingLotRequestDto();

        boolean operatesNonStopIsTrue = operatesNonStopEnabledValidator.isValid(parkingLotRequestDto, null);

        assertTrue(operatesNonStopIsTrue);
    }

    @Test
    void isValid_OperatesNonStopIsFalseAndStartTimeIsNull_ReturnsTrue() {
        ParkingLotRequestDto parkingLotRequestDto = ParkingLotDTOUtils.getPreparedParkingLotRequestDtoWithNullStartTimeAndNonStopFalse();

        boolean operatesNonStopIsTrue = operatesNonStopEnabledValidator.isValid(parkingLotRequestDto, null);

        assertFalse(operatesNonStopIsTrue);
    }

    @Test
    void isValid_OperatesNonStopIsFalseAndEndTimeIsNull_ReturnsTrue() {
        ParkingLotRequestDto parkingLotRequestDto = ParkingLotDTOUtils.getPreparedParkingLotRequestDtoWithNullEndTimeAndNonStopFalse();

        boolean operatesNonStopIsTrue = operatesNonStopEnabledValidator.isValid(parkingLotRequestDto, null);

        assertFalse(operatesNonStopIsTrue);
    }
}