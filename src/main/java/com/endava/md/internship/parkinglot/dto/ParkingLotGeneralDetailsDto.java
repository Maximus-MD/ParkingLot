package com.endava.md.internship.parkinglot.dto;

public record ParkingLotGeneralDetailsDto(
        Long parkingLotId,
        String name,
        String operatingHours,
        String operatingDays,
        boolean isTemporaryClosed,
        boolean operatesNonStop
) {
}
