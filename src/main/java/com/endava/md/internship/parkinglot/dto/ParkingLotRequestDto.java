package com.endava.md.internship.parkinglot.dto;

import com.endava.md.internship.parkinglot.validation.annotation.AccessibleParkingLevel;
import com.endava.md.internship.parkinglot.validation.annotation.AccessibleParkingSpot;
import com.endava.md.internship.parkinglot.validation.annotation.DuplicateWorkingDays;
import com.endava.md.internship.parkinglot.validation.annotation.EmptyWorkingDays;
import com.endava.md.internship.parkinglot.validation.annotation.OperatesNonStopEnabled;
import com.endava.md.internship.parkinglot.validation.annotation.UniqueParkingAddress;
import com.endava.md.internship.parkinglot.validation.annotation.UniqueParkingName;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.sql.Time;
import java.util.List;
import java.util.Map;

@Builder
@OperatesNonStopEnabled
@EmptyWorkingDays
public record ParkingLotRequestDto(
        @NotBlank(message = "{message.empty-parking-name}")
        @Size(max = 70, message = "{message.parking-name-length-exceeded}")
        @UniqueParkingName
        @Schema(description = "The name of the parking lot. It must not be blank and can be up to 70 characters long.",
                example = "Port Mall Parking")
        String name,

        @NotBlank(message = "{message.empty-parking-address}")
        @Size(max = 70, message = "{message.parking-address-length-exceeded}")
        @UniqueParkingAddress
        @Schema(description = "The address of the parking lot. It must not be blank and can be up to 70 characters long.",
                example = "str. Mihail Sadoveanu 42")
        String address,

        @Schema(description = "The opening time of the parking lot (for example '09:00:00').")
        Time startTime,

        @Schema(description = "The closing time of the parking lot (for example '23:00:00').")
        Time endTime,

        @Schema(description = "Indicates if the parking lot operates nonstop (24/7).",
                example = "true")
        boolean operatesNonStop,

        @Schema(description = "Indicates if the parking lot is temporarily closed.",
                example = "false")
        boolean temporaryClosed,


        @DuplicateWorkingDays
        @Schema(description = "The list of working days when the parking lot is open. Working days should be provided in a valid format.",
                example = "[{ \"day\": \"MONDAY\" }, { \"day\": \"TUESDAY\" },{ \"day\": \"TUESDAY\" }]")
        List<WorkingDayDto> workingDays,

        @Size(min = 1, max = 5, message = "{message.incorrect-parking-levels-count}")
        @AccessibleParkingLevel
        @AccessibleParkingSpot
        @Schema(description = "A map of parking levels with the respective number of available spots. The number of levels should be between 1 and 5.",
                example = "{\"A\": 11, \"B\": 15}")
        Map<String, Integer> parkingLevels
) {
}