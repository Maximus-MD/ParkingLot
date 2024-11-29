package com.endava.md.internship.parkinglot.validation.validator;

import com.endava.md.internship.parkinglot.dto.ParkingLotRequestDto;
import com.endava.md.internship.parkinglot.validation.annotation.OperatesNonStopEnabled;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class OperatesNonStopEnabledValidator implements ConstraintValidator<OperatesNonStopEnabled, ParkingLotRequestDto> {

    @Override
    public boolean isValid(ParkingLotRequestDto parkingLotRequestDto, ConstraintValidatorContext constraintValidatorContext) {
        if (parkingLotRequestDto.operatesNonStop()) {
            return true;
        }

        return parkingLotRequestDto.startTime() != null && parkingLotRequestDto.endTime() != null;
    }
}
