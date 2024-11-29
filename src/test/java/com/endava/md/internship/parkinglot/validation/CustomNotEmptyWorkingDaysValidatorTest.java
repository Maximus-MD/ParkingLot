package com.endava.md.internship.parkinglot.validation;

import com.endava.md.internship.parkinglot.dto.WorkingDayDto;
import com.endava.md.internship.parkinglot.validation.validator.NotEmptyWorkingDaysValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class CustomNotEmptyWorkingDaysValidatorTest {

    private NotEmptyWorkingDaysValidator notEmptyWorkingDaysValidator;

    @BeforeEach
    void setUp() {
        notEmptyWorkingDaysValidator = new NotEmptyWorkingDaysValidator();
    }

    @Test
    void isValid_WorkingDaysIsNotEmpty_ReturnsTrue() {
        List<WorkingDayDto> emptyWorkingDays = new ArrayList<>();
        emptyWorkingDays.add(new WorkingDayDto(DayOfWeek.FRIDAY));

        boolean isEmpty = notEmptyWorkingDaysValidator.isValid(emptyWorkingDays, null);

        assertTrue(isEmpty);
    }

    @Test
    void isValid_WorkingDaysIsEmpty_ReturnsFalse() {
        List<WorkingDayDto> emptyWorkingDays = Collections.emptyList();

        boolean isEmpty = notEmptyWorkingDaysValidator.isValid(emptyWorkingDays, null);

        assertFalse(isEmpty);
    }

    @Test
    void isValid_IncorrectWorkingDaysCount_ReturnsFalse() {
        List<WorkingDayDto> workingDays = new ArrayList<>(Arrays.asList(
                new WorkingDayDto(DayOfWeek.MONDAY),
                new WorkingDayDto(DayOfWeek.TUESDAY),
                new WorkingDayDto(DayOfWeek.WEDNESDAY),
                new WorkingDayDto(DayOfWeek.THURSDAY),
                new WorkingDayDto(DayOfWeek.FRIDAY),
                new WorkingDayDto(DayOfWeek.SATURDAY),
                new WorkingDayDto(DayOfWeek.SUNDAY),
                new WorkingDayDto(DayOfWeek.MONDAY)
        ));

        boolean invalidWorkingDaysCount = notEmptyWorkingDaysValidator.isValid(workingDays, null);

        assertFalse(invalidWorkingDaysCount);
    }

}