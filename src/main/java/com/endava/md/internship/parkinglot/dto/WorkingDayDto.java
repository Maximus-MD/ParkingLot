package com.endava.md.internship.parkinglot.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.DayOfWeek;

public record WorkingDayDto(
        @Schema(description = "The day of the week when the parking lot operates.",
                example = "MONDAY")
        DayOfWeek day
){
}