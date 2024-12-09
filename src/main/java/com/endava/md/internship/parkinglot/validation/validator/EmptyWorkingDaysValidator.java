package com.endava.md.internship.parkinglot.validation.validator;

import com.endava.md.internship.parkinglot.dto.ParkingLotRequestDto;
import com.endava.md.internship.parkinglot.validation.annotation.EmptyWorkingDays;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EmptyWorkingDaysValidator
        implements ConstraintValidator<EmptyWorkingDays, ParkingLotRequestDto>
{

    @Override
    public boolean isValid(ParkingLotRequestDto parkingLotRequestDto, ConstraintValidatorContext constraintValidatorContext) {
        if (parkingLotRequestDto.operatesNonStop()) {
            return true;
        }

        return !parkingLotRequestDto.workingDays().isEmpty();
    }
}
