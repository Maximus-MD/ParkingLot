package com.endava.md.internship.parkinglot.validation.validator;

import com.endava.md.internship.parkinglot.dto.WorkingDayDto;
import com.endava.md.internship.parkinglot.validation.annotation.NotEmptyWorkingDays;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class NotEmptyWorkingDaysValidator implements ConstraintValidator<NotEmptyWorkingDays, List<WorkingDayDto>> {

    @Override
    public boolean isValid(List<WorkingDayDto> workingDaysDto, ConstraintValidatorContext constraintValidatorContext) {
        return !workingDaysDto.isEmpty();
    }
}
