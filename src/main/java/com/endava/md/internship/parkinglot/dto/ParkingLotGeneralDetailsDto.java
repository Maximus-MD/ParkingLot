package com.endava.md.internship.parkinglot.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ParkingLotGeneralDetailsDto(
        Long parkingLotId,
        String name,
        String operatingHours,
        List<String> operatingDays,
        boolean isTemporaryClosed,
        boolean operatesNonStop,
        int totalSpots,
        int availableSpots,
        double loadPercentage
) {
}