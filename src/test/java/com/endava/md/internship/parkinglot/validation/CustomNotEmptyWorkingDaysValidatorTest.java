package com.endava.md.internship.parkinglot.validation;

import com.endava.md.internship.parkinglot.dto.WorkingDayDto;
import com.endava.md.internship.parkinglot.validation.validator.NotEmptyWorkingDaysValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

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

        assertThat(isEmpty).isTrue();
    }

    @Test
    void isValid_WorkingDaysIsEmpty_ReturnsFalse() {
        List<WorkingDayDto> emptyWorkingDays = Collections.emptyList();

        boolean isEmpty = notEmptyWorkingDaysValidator.isValid(emptyWorkingDays, null);

        assertThat(isEmpty).isFalse();
    }

}