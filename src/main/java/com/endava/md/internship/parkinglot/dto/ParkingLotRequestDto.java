package com.endava.md.internship.parkinglot.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.sql.Time;
import java.util.List;
import java.util.Map;

public record ParkingLotRequestDto(

        @Size(max = 70)
        @NotBlank
        String name,

        @Size(max = 70)
        @NotBlank
        String address,

        Time startTime,

        Time endTime,

        boolean operatesNonStop,

        boolean temporaryClosed,

        @NotBlank
        List<WorkingDayDto> workingDays,

        @Size(min = 1, max = 5)
        @NotBlank
        Map<String, Integer> parkingLevels
) {
}