package com.endava.md.internship.parkinglot.dto;

import com.endava.md.internship.parkinglot.validation.annotation.NotEmptyWorkingTimes;
import com.endava.md.internship.parkinglot.validation.annotation.UniqueParkingAddress;
import com.endava.md.internship.parkinglot.validation.annotation.UniqueParkingName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.sql.Time;
import java.util.List;
import java.util.Map;

public record ParkingLotRequestDto(

        @NotBlank(message = "{message.empty-parking-name}")
        @Size(max = 70, message = "{message.parking-name-length-exceeded}")
        @UniqueParkingName
        String name,

        @NotBlank(message = "{message.empty-parking-address}")
        @Size(max = 70, message = "{message.parking-address-length-exceeded}")
        @UniqueParkingAddress
        String address,

        @NotNull(message = "{parking-time-null-value}")
        Time startTime,

        @NotNull(message = "{parking-time-null-value}")
        Time endTime,

        boolean operatesNonStop,

        boolean temporaryClosed,

        @NotEmptyWorkingTimes
        List<WorkingDayDto> workingDays,

        @Size(min = 1, max = 5, message = "{message.invalid-parking-levels-value}")
        Map<String, Integer> parkingLevels
) {
}