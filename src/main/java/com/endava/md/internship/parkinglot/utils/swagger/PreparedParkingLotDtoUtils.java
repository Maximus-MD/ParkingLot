package com.endava.md.internship.parkinglot.utils.swagger;

public class PreparedParkingLotDtoUtils {

    private PreparedParkingLotDtoUtils() {
    }

    public static final String PREPARED_PARKING_LOT_LIST_RESPONSE_DTO = """
            [
              {
                "parkingLotId": 125,
                "name": "Kaufland",
                "operatingHours": "09:00 - 23:00",
                "operatingDays": "MONDAY/FRIDAY",
                "isTemporaryClosed": false,
                "operatesNonStop": false
              },
              {
                "parkingLotId": 136,
                "name": "Kaufland BBotanica",
                "operatingHours": "",
                "operatingDays": "",
                "isTemporaryClosed": false,
                "operatesNonStop": true
              },
              {
                "parkingLotId": 137,
                "name": "MallDovaa",
                "operatingHours": "22:00 - 22:00",
                "operatingDays": "MONDAY/TUESDAY",
                "isTemporaryClosed": false,
                "operatesNonStop": false
              },
              {
                "parkingLotId": 138,
                "name": "Port Mall",
                "operatingHours": "",
                "operatingDays": "",
                "isTemporaryClosed": false,
                "operatesNonStop": true
              }
            ]
            """;
}
