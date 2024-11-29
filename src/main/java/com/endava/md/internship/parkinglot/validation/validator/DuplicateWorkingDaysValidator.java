package com.endava.md.internship.parkinglot.validation.validator;

import com.endava.md.internship.parkinglot.dto.WorkingDayDto;
import com.endava.md.internship.parkinglot.validation.annotation.DuplicateWorkingDays;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Collections;
import java.util.List;

public class DuplicateWorkingDaysValidator implements ConstraintValidator<DuplicateWorkingDays, List<WorkingDayDto>> {
    @Override
    public boolean isValid(List<WorkingDayDto> workingDaysDto, ConstraintValidatorContext constraintValidatorContext) {
        return workingDaysDto.stream().noneMatch(dto -> Collections.frequency(workingDaysDto, dto) > 1);
    }
}
