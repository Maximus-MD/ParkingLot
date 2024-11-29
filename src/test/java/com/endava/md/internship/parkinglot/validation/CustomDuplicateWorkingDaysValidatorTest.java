package com.endava.md.internship.parkinglot.validation;

import com.endava.md.internship.parkinglot.dto.WorkingDayDto;
import com.endava.md.internship.parkinglot.validation.validator.DuplicateWorkingDaysValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.DayOfWeek;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
class CustomDuplicateWorkingDaysValidatorTest {

    private DuplicateWorkingDaysValidator duplicateWorkingDaysValidator;

    @BeforeEach
    void setUp() {
        duplicateWorkingDaysValidator = new DuplicateWorkingDaysValidator();
    }

    @Test
    void isValid_ContainsDuplicateWorkingDays_ReturnsFalse() {
        List<WorkingDayDto> workingDays = List.of(
                new WorkingDayDto(DayOfWeek.MONDAY),
                new WorkingDayDto(DayOfWeek.TUESDAY),
                new WorkingDayDto(DayOfWeek.WEDNESDAY),
                new WorkingDayDto(DayOfWeek.MONDAY)
                );

        boolean isValid = duplicateWorkingDaysValidator.isValid(workingDays, null);

        assertThat(isValid).isFalse();
    }

    @Test
    void isValid_DoesNotContainsDuplicateWorkingDays_ReturnsTrue() {
        List<WorkingDayDto> workingDays = List.of(
                new WorkingDayDto(DayOfWeek.MONDAY),
                new WorkingDayDto(DayOfWeek.TUESDAY),
                new WorkingDayDto(DayOfWeek.WEDNESDAY)
        );

        boolean isValid = duplicateWorkingDaysValidator.isValid(workingDays, null);

        assertThat(isValid).isTrue();

    }
}