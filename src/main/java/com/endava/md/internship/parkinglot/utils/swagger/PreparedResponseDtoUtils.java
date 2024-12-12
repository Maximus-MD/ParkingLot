package com.endava.md.internship.parkinglot.utils.swagger;


public class PreparedResponseDtoUtils {

    private PreparedResponseDtoUtils() {
    }

    public static final String PREPARED_SUCCESS_RESPONSE_DTO_WITH_JWT = """
            {
            "success": true,
            "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqbzEyaG4xOTkyQGdtYWlsLmNvbSIsInJvbGUiOiJST0xFX0FETUlOIiwiZXhwIjoxNzMzOTE1MDAzLCJ0b2tlbl90eXBlIjoiYWNjZXNzIn0.ye11vzNLtpQHkugN3kR5QAbEzm0vYwOVup6f9_WVZzE",
            "error": []
            }""";

    public static final String PREPARED_BAD_CREDENTIALS_RESPONSE_DTO = """
            {
            "success": false,
            "token": null,
            "error": [
                 4043
             ]
            }""";

    public static final String PREPARED_USER_NOT_FOUND_RESPONSE_DTO = """
            {
            "success": false,
            "token": null,
            "error": [
                 4033
             ]
            }""";

    public static final String PREPARED_FAILED_AUTHENTICATION_RESPONSE_DTO = """
            {
            "success": false,
            "token": null,
            "error": [
                     4044
            ]
            }""";

    public static final String PREPARED_FAILED_RESPONSE_DTO_WHEN_USER_IS_NOT_FOUND = """
            {
            "success": false,
            "token": null,
            "error": [
                 4033
            ]
            }""";

    public static final String PREPARED_SUCCESS_RESPONSE_DTO = """
            {
            "success": true,
            "error": []
            }""";

    public static final String PREPARED_FAILED_RESPONSE_DTO = """
            {
            "success": false,
            "token": null,
            "error": [
                   1001,
                   1002
            ]
            }""";

    public static final String PREPARED_NONEXISTENT_PARKING_SPOT_ID_RESPONSE_DTO = """
            {
            "success": false,
            "token": null,
            "error": [
                   1017
            ]
            }""";

    public static final String PREPARED_INVALID_REGISTRATION_DATA_DTO = """
            {
                 "success": false,
                 "token": null,
                 "error": [
                     3001,
                     3002
                 ]
             }""";

    public static final String PREPARED_PARKING_LOT_DOES_NOT_EXIST_DATA_DTO = """
            {
                  "success": false,
                  "error": [
                      1013
                  ]
            }""";
}

