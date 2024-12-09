package com.endava.md.internship.parkinglot.dto;

import com.endava.md.internship.parkinglot.validation.annotation.CorrectParkingSpotType;
import lombok.Builder;

@Builder
public record ParkingSpotTypeDto(
        @CorrectParkingSpotType
        String parkingSpotType
) {
}