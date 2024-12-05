package com.endava.md.internship.parkinglot.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ParkingSpotDto(

        @NotBlank
        String parkingName,

        @NotBlank
        String spotName,

        @NotBlank
        @Email(message = "{message.invalid-email}")
        String email
){}