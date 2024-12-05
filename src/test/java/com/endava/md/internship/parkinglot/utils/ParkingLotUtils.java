package com.endava.md.internship.parkinglot.utils;

import com.endava.md.internship.parkinglot.model.ParkingLevel;
import com.endava.md.internship.parkinglot.model.ParkingLot;
import com.endava.md.internship.parkinglot.model.ParkingSpot;
import com.endava.md.internship.parkinglot.model.WorkingDay;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import static com.endava.md.internship.parkinglot.model.ParkingSpotType.REGULAR;
import static java.time.DayOfWeek.MONDAY;

public class ParkingLotUtils {
    private static final ParkingLot preparedParkingLot = new ParkingLot();
    private static final ParkingLevel preparedParkingLevel = new ParkingLevel();

    public static ParkingLot getPreparedParkingLot() {
        preparedParkingLot.setParkingLotId(1L);
        preparedParkingLot.setName("Kaufland");
        preparedParkingLot.setAddress("str. Mircea cel Batran 20");
        preparedParkingLot.setStartTime(Time.valueOf("10:30:00"));
        preparedParkingLot.setEndTime(Time.valueOf("20:30:00"));
        preparedParkingLot.setOperatesNonStop(false);
        preparedParkingLot.setWorkingDays(getPreparedWorkingDays());
        preparedParkingLot.setParkingLevels(getPreparedParkingLevels());

        return preparedParkingLot;
    }

    public static ParkingLot getPreparedParkingLotWithNullTime() {
        preparedParkingLot.setParkingLotId(1L);
        preparedParkingLot.setName("Kaufland");
        preparedParkingLot.setAddress("str. Mircea cel Batran 20");
        preparedParkingLot.setStartTime(null);
        preparedParkingLot.setEndTime(null);
        preparedParkingLot.setOperatesNonStop(true);
        preparedParkingLot.setWorkingDays(getPreparedWorkingDays());
        preparedParkingLot.setParkingLevels(getPreparedParkingLevels());

        return preparedParkingLot;
    }

    public static List<ParkingLevel> getPreparedParkingLevels() {
        preparedParkingLevel.setLevelId(1L);
        preparedParkingLevel.setLevelName("A");
        preparedParkingLevel.setParkingLot(preparedParkingLot);
        preparedParkingLevel.setParkingSpots(getPreparedParkingSpot());

        List<ParkingLevel> parkingLevels = new ArrayList<>();
        parkingLevels.add(preparedParkingLevel);

        return parkingLevels;
    }

    public static List<ParkingSpot> getPreparedParkingSpot() {
        ParkingSpot parkingSpot = new ParkingSpot();

        parkingSpot.setSpotId(1L);
        parkingSpot.setName("A-001");
        parkingSpot.setType(REGULAR);
        parkingSpot.setOccupied(false);
        parkingSpot.setParkingLevel(preparedParkingLevel);

        List<ParkingSpot> parkingSpots = new ArrayList<>();
        parkingSpots.add(parkingSpot);

        return parkingSpots;
    }

    public static List<WorkingDay> getPreparedWorkingDays() {
        WorkingDay workingDay1 = new WorkingDay();

        workingDay1.setWorkingDayId(1L);
        workingDay1.setDayName(MONDAY);

        List<WorkingDay> workingDays = new ArrayList<>();

        workingDays.add(workingDay1);

        return workingDays;
    }
}
