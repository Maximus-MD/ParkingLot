package com.endava.md.internship.parkinglot.validation;

import com.endava.md.internship.parkinglot.dto.WorkingDayDto;
import com.endava.md.internship.parkinglot.validation.validator.NotEmptyWorkingTimesValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class CustomNotEmptyWorkingTimesValidatorTest {

    private NotEmptyWorkingTimesValidator notEmptyWorkingTimesValidator;

    @BeforeEach
    void setUp() {
        notEmptyWorkingTimesValidator = new NotEmptyWorkingTimesValidator();
    }

    @Test
    void isValid_WorkingTimesIsNotEmpty_ReturnsTrue() {
        List<WorkingDayDto> emptyWorkingDays = new ArrayList<>();
        emptyWorkingDays.add(new WorkingDayDto(DayOfWeek.FRIDAY));

        boolean isEmpty = notEmptyWorkingTimesValidator.isValid(emptyWorkingDays, null);

        assertTrue(isEmpty);
    }

    @Test
    void isValid_WorkingTimesIsEmpty_ReturnsFalse() {
        List<WorkingDayDto> emptyWorkingDays = Collections.emptyList();

        boolean isEmpty = notEmptyWorkingTimesValidator.isValid(emptyWorkingDays, null);

        assertFalse(isEmpty);
    }

}