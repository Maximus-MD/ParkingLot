package com.endava.md.internship.parkinglot.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)

public record ParkingLotGeneralDetailsDto(
        @Schema(description = "The unique identifier for the parking lot.",
                example = "108")
        Long parkingLotId,

        @Schema(description = "The name of the parking lot.",
                example = "Port Mall Parking")
        String name,

        @Schema(description = "The operating hours of the parking lot, indicating when it is open.",
                example = "08:00:00 - 10:00:00")
        String operatingHours,

        @Schema(description = "The days of the week when the parking lot is operational. E.g., Monday to Friday.",
                example = "MONDAY, TUESDAY, WEDNESDAY, FRIDAY")
        List<String> operatingDays,

        @Schema(description = "Indicates if the parking lot is temporarily closed.",
                example = "false")
        boolean isTemporaryClosed,

        @Schema(description = "Indicates if the parking lot operates nonstop (24/7).",
                example = "true")
        boolean operatesNonStop,
        int totalSpots,
        int availableSpots,
        double loadPercentage
) {
}