package com.endava.md.internship.parkinglot.utils;

import com.endava.md.internship.parkinglot.dto.ParkingSpotResponseDto;
import com.endava.md.internship.parkinglot.model.ParkingSpotType;

public class ParkingSpotResponseDTOUtils {
    public static ParkingSpotResponseDto getPreparedParkingSpotResponseDto(){
        return new ParkingSpotResponseDto(
                10L,
                "A-001",
                ParkingSpotType.HANDICAP,
                false,
                10L
        );
    }

    public static ParkingSpotResponseDto getPreparedParkingSpotResponseDtoWithHandicapType(){
        return new ParkingSpotResponseDto(
                10L,
                "A-001",
                ParkingSpotType.HANDICAP,
                false,
                10L
        );
    }

    public static ParkingSpotResponseDto getPreparedParkingSpotResponseDtoWithRegularType(){
        return new ParkingSpotResponseDto(
                10L,
                "A-001",
                ParkingSpotType.REGULAR,
                false,
                10L
        );
    }

    public static ParkingSpotResponseDto getPreparedParkingSpotResponseDtoWithValueTrueForOccupiedField(){
        return new ParkingSpotResponseDto(
                3L,
                "A-100",
                ParkingSpotType.REGULAR,
                true,
                5L
        );
    }
}
