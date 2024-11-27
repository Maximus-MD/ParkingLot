package com.endava.md.internship.parkinglot.dto;

import lombok.Builder;

import java.util.Set;

@Builder
public record ParkingLotResponseDto(

        boolean success,

        Set<Integer> error
) {
}