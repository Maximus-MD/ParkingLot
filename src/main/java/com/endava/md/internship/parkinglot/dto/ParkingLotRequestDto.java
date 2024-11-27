package com.endava.md.internship.parkinglot.dto;

import com.endava.md.internship.parkinglot.validation.annotation.UniqueAddress;
import com.endava.md.internship.parkinglot.validation.annotation.UniqueParkingName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.sql.Time;
import java.util.List;
import java.util.Map;

public record ParkingLotRequestDto(

        @NotBlank
        @Size(max = 70)
        @UniqueParkingName
        String name,

        @Size(max = 70)
        @NotBlank
        @UniqueAddress
        String address,

        @NotBlank
        Time startTime,

        @NotBlank
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