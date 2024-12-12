package com.endava.md.internship.parkinglot.utils.swagger;

public class PreparedParkingSpotDtoUtils {
    private PreparedParkingSpotDtoUtils() {
    }

    public static final String PREPARED_PARKING_SPOT_DTO = """
            {
                "spotId": 2771,
                "name": "A-001",
                "type": "REGULAR",
                "occupied": false,
                "parkingLevelId": 173
            }
            """;

    public static final String PREPARED_PARKING_SPOT_TYPES = """
            [
                "REGULAR",
                "FAMILY_FRIENDLY",
                "HANDICAP",
                "TEMP_CLOSED"
            ]
            """;

    public static final String PREPARED_SUCCESSFULLY_RESERVED_PARKING_SPOT_DTO = """
            {
            "success": true,
            "error": []
            }
            """;

    public static final String PREPARED_FAILED_RESERVED_PARKING_SPOT_DTO = """
            {
            "success": false,
            "error": [
                    1018
                ]
            }
            """;
}
