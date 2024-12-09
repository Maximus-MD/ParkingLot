package com.endava.md.internship.parkinglot.utils;


import com.endava.md.internship.parkinglot.model.ParkingLevel;
import com.endava.md.internship.parkinglot.model.ParkingSpot;
import com.endava.md.internship.parkinglot.model.ParkingSpotType;

public class ParkingSpotUtils {
    public static ParkingSpot getPreparedParkingSpot() {
        return new ParkingSpot(
                10L,
                "A-001",
                ParkingSpotType.REGULAR,
                false,
                ParkingLevel.builder().levelId(10L).build()
        );
    }

    public static ParkingSpot getPreparedParkingSpotWithValueTrueForOccupiedField() {
        return new ParkingSpot(
                10L,
                "A-001",
                ParkingSpotType.REGULAR,
                true,
                ParkingLevel.builder().levelId(10L).build()
        );
    }
}
