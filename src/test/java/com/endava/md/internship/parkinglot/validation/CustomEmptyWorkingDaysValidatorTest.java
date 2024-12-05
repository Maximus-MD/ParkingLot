package com.endava.md.internship.parkinglot.validation;

import com.endava.md.internship.parkinglot.dto.ParkingLotRequestDto;
import com.endava.md.internship.parkinglot.utils.ParkingLotDTOUtils;
import com.endava.md.internship.parkinglot.validation.validator.EmptyWorkingDaysValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
class CustomEmptyWorkingDaysValidatorTest {

    private EmptyWorkingDaysValidator validator;

    @BeforeEach
    void setUp() {
        validator = new EmptyWorkingDaysValidator();
    }

    @Test
    void isValid_EmptyWorkingDaysWhenOperatesNonStopEnabled() {
        ParkingLotRequestDto parkingLotRequestDto =
                ParkingLotDTOUtils.getPreparedParkingLotRequestDtoWithOperatesNonStopEnabled();

        boolean isValid = validator.isValid(parkingLotRequestDto, null);

        assertThat(isValid).isTrue();
    }

    @Test
    void isValid_NonEmptyWorkingDaysWhenOperatesNonStopEnabled() {
        ParkingLotRequestDto parkingLotRequestDto =
                ParkingLotDTOUtils.getPreparedParkingLotRequestDtoWithOperatesNonStopDisabled();


        boolean isValid = validator.isValid(parkingLotRequestDto, null);

        assertThat(isValid).isFalse();
    }

}
