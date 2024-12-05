package com.endava.md.internship.parkinglot.utils;

import com.endava.md.internship.parkinglot.dto.ParkingLotRequestDto;
import com.endava.md.internship.parkinglot.dto.ParkingLotResponseDto;
import com.endava.md.internship.parkinglot.dto.WorkingDayDto;

import java.sql.Time;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static java.time.DayOfWeek.FRIDAY;
import static java.time.DayOfWeek.MONDAY;
import static java.util.Collections.emptySet;

public class ParkingLotDTOUtils {
    public static ParkingLotRequestDto getPreparedParkingLotRequestDto() {
        return new ParkingLotRequestDto(
                "Kaufland",
                "str. Mircea cel Batran 20",
                Time.valueOf("10:30:00"),
                Time.valueOf("20:30:00"),
                false,
                false,
                List.of(new WorkingDayDto(MONDAY)),
                Map.of("A", 10)
                );
    }

    public static ParkingLotRequestDto getPreparedParkingLotRequestDtoWithInvalidDay() {
        return new ParkingLotRequestDto(
                "Kaufland",
                "str. Mircea cel Batran 20",
                Time.valueOf("10:30:00"),
                Time.valueOf("20:30:00"),
                false,
                false,
                List.of(new WorkingDayDto(FRIDAY)),
                Map.of("A", 10)
        );
    }

    public static ParkingLotRequestDto getPreparedParkingLotRequestDtoNullTime() {
        return new ParkingLotRequestDto(
                "Kaufland",
                "str. Mircea cel Batran 20",
                null,
                null,
                true,
                false,
                List.of(new WorkingDayDto(MONDAY)),
                Map.of("A", 10)
        );
    }

    public static ParkingLotRequestDto getPreparedParkingLotRequestDtoWithOperatesNonStopTrue() {
        return new ParkingLotRequestDto(
                "Kaufland",
                "str. Mircea cel Batran 20",
                Time.valueOf("10:30:00"),
                Time.valueOf("20:30:00"),
                true,
                false,
                List.of(new WorkingDayDto(MONDAY)),
                Map.of("A", 10)
        );
    }

    public static ParkingLotRequestDto getPreparedParkingLotRequestDtoWithNullTimeAndNonStopFalse() {
        return new ParkingLotRequestDto(
                "Kaufland",
                "str. Mircea cel Batran 20",
                null,
                null,
                false,
                false,
                List.of(new WorkingDayDto(MONDAY)),
                Map.of("A", 10)
        );
    }

    public static ParkingLotRequestDto getPreparedParkingLotRequestDtoWithNullStartTimeAndNonStopFalse() {
        return new ParkingLotRequestDto(
                "Kaufland",
                "str. Mircea cel Batran 20",
                null,
                Time.valueOf("20:30:00"),
                false,
                false,
                List.of(new WorkingDayDto(MONDAY)),
                Map.of("A", 10)
        );
    }

    public static ParkingLotRequestDto getPreparedParkingLotRequestDtoWithNullEndTimeAndNonStopFalse() {
        return new ParkingLotRequestDto(
                "Kaufland",
                "str. Mircea cel Batran 20",
                Time.valueOf("20:30:00"),
                null,
                false,
                false,
                List.of(new WorkingDayDto(MONDAY)),
                Map.of("A", 10)
        );
    }

    public static ParkingLotResponseDto getPreparedParkingLotResponseDto() {
        return new ParkingLotResponseDto(
                true,
                emptySet()
        );
    }

    public static ParkingLotRequestDto getPreparedParkingLotRequestDtoWithOperatesNonStopEnabled() {
        return new ParkingLotRequestDto(
                "Kaufland",
                "str. Mircea cel Batran 20",
                null,
                null,
                true,
                false,
                Collections.emptyList(),
                Map.of("A", 10)
        );
    }

    public static ParkingLotRequestDto getPreparedParkingLotRequestDtoWithOperatesNonStopDisabled() {
        return new ParkingLotRequestDto(
                "Kaufland",
                "str. Mircea cel Batran 20",
                null,
                null,
                false,
                false,
                Collections.emptyList(),
                Map.of("A", 10)
        );
    }

}
