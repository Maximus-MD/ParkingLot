package com.endava.md.internship.parkinglot.dto;

import com.endava.md.internship.parkinglot.model.ParkingSpotType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record ParkingSpotResponseDto(
        @Schema(description = "The unique identifier for the parking spot.",
                example = "2574")
        Long spotId,

        @Schema(description = "The name of the parking spot, such as its number or label.",
                example = "A-101")
        String name,

        @Schema(description = "The type of parking spot (regular, handicap, family_friendly, temp_closed)",
                example = "Regular")
        ParkingSpotType type,

        @Schema(description = "Indicates whether the parking spot is currently occupied.",
                example = "false")
        boolean occupied,

        @Schema(description = "The ID of the parking level where this parking spot is located.",
                example = "2")
        Long parkingLevelId
) {
}
