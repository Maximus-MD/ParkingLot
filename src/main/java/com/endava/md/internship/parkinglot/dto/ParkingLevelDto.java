package com.endava.md.internship.parkinglot.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record ParkingLevelDto(
        @NotBlank
        String levelName,

        int totalSpots,

        @NotBlank
        List<ParkingSpotDto> spots) {
}
