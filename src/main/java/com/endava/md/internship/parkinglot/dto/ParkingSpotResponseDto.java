package com.endava.md.internship.parkinglot.dto;

import com.endava.md.internship.parkinglot.model.ParkingSpotType;
import lombok.Builder;

@Builder
public record ParkingSpotResponseDto(

        Long spotId,

        String name,

        ParkingSpotType type,

        boolean occupied,

        Long parkingLevelId
) {
}
