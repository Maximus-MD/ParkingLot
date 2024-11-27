package com.endava.md.internship.parkinglot.validation.validator;

import com.endava.md.internship.parkinglot.dto.WorkingDayDto;
import com.endava.md.internship.parkinglot.validation.annotation.NotEmptyWorkingTimes;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class NotEmptyWorkingTimesValidator implements ConstraintValidator<NotEmptyWorkingTimes, List<WorkingDayDto>> {
    @Override
    public void initialize(NotEmptyWorkingTimes constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(List<WorkingDayDto> workingDaysDto, ConstraintValidatorContext constraintValidatorContext) {
        return !workingDaysDto.isEmpty();
    }
}
