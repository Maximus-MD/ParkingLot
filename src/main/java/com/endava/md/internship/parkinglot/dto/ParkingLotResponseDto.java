package com.endava.md.internship.parkinglot.dto;

import lombok.Builder;

import java.sql.Time;

@Builder
public record ParkingLotResponseDto(
        String name,

        String address,

        Time startTime,

        Time endTime,

        Boolean operatesNonStop,

        Boolean temporaryClosed,

        boolean success
) {
}