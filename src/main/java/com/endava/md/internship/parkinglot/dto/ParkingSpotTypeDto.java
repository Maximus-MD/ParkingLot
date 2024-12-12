package com.endava.md.internship.parkinglot.dto;

import com.endava.md.internship.parkinglot.validation.annotation.CorrectParkingSpotType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record ParkingSpotTypeDto(
        @CorrectParkingSpotType
        @Schema(description = "The type of parking spot (regular, handicap, family_friendly, temp_closed)",
                example = "Regular")
        String parkingSpotType
) {
}