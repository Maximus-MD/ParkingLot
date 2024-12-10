package com.endava.md.internship.parkinglot.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ParkingSpotDto(

        @NotBlank(message = "${message.empty-parking-name}")
        String parkingName,

        @NotBlank(message = "${message.empty-parking-spot}")
        String spotName,

        @NotBlank
        @Email(message = "{message.invalid-email}")
        String email
){}