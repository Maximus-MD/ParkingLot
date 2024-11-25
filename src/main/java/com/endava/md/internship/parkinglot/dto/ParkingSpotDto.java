package com.endava.md.internship.parkinglot.dto;

import com.endava.md.internship.parkinglot.model.ParkingSpotType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record ParkingSpotDto(

        @NotBlank
        @Pattern(regexp = "^[A-E]-(00[1-9]|0[1-9][0-9]|1[0-4][0-9]|150)$", message = "Invalid name. Format: [A-E]-XXX (XXX between 001-150).")
        String name,

        @NotBlank
        boolean state,

        @NotBlank
        ParkingSpotType type
) {
}
