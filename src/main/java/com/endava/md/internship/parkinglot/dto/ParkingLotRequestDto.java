package com.endava.md.internship.parkinglot.dto;

import com.endava.md.internship.parkinglot.validation.annotation.AccessibleParkingLevel;
import com.endava.md.internship.parkinglot.validation.annotation.NotEmptyWorkingDays;
import com.endava.md.internship.parkinglot.validation.annotation.OperatesNonStopEnabled;
import com.endava.md.internship.parkinglot.validation.annotation.UniqueParkingAddress;
import com.endava.md.internship.parkinglot.validation.annotation.UniqueParkingName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.sql.Time;
import java.util.List;
import java.util.Map;

@OperatesNonStopEnabled
public record ParkingLotRequestDto(

        @NotBlank(message = "{message.empty-parking-name}")
        @Size(max = 70, message = "{message.parking-name-length-exceeded}")
        @UniqueParkingName
        String name,

        @NotBlank(message = "{message.empty-parking-address}")
        @Size(max = 70, message = "{message.parking-address-length-exceeded}")
        @UniqueParkingAddress
        String address,

        Time startTime,

        Time endTime,

        boolean operatesNonStop,

        boolean temporaryClosed,

        @NotEmptyWorkingDays
        List<WorkingDayDto> workingDays,

        @Size(min = 1, max = 5, message = "{message.incorrect-parking-levels-count}")
        @AccessibleParkingLevel
        Map<String, Integer> parkingLevels
) {
}