package com.endava.md.internship.parkinglot.dto;

import com.endava.md.internship.parkinglot.validation.annotation.CorrectParkingSpotType;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record ParkingSpotTypeDto(
        @NotBlank(message = "{message.blank-parking-spot-type}")
        @CorrectParkingSpotType
        String parkingSpotType
) {
}