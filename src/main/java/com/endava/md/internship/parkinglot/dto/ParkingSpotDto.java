package com.endava.md.internship.parkinglot.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ParkingSpotDto(

        @NotBlank(message = "${message.empty-parking-name}")
        @Schema(
                description = "The name of the parking lot where the parking spot is located.",
                example = "Port Mall"
        )
        String parkingName,

        @NotBlank(message = "${message.empty-parking-spot}")
        @Schema(
                description = "The name of the specific parking spot within the parking lot.",
                example = "A-001"
        )
        String spotName,

        @NotBlank
        @Email(message = "{message.invalid-email}")
        @Schema(
                description = "The email address of the person responsible for the parking spot.",
                example = "user@example.com"
        )
        String email
){}