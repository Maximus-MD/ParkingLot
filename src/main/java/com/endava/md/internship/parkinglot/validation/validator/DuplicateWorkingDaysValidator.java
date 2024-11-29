package com.endava.md.internship.parkinglot.validation.validator;

import com.endava.md.internship.parkinglot.dto.WorkingDayDto;
import com.endava.md.internship.parkinglot.validation.annotation.DuplicateWorkingDays;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DuplicateWorkingDaysValidator implements ConstraintValidator<DuplicateWorkingDays, List<WorkingDayDto>> {
    @Override
    public boolean isValid(List<WorkingDayDto> workingDaysDto, ConstraintValidatorContext constraintValidatorContext) {
        return containsDuplicates(workingDaysDto);
    }

    public boolean containsDuplicates(List<WorkingDayDto> workingDaysDto) {
        Set<WorkingDayDto> workingDays = new HashSet<>();
        for(WorkingDayDto workingDay : workingDaysDto) {
            if(!workingDays.add(workingDay)) {
                return false;
            }
        }
        return true;
    }
}
