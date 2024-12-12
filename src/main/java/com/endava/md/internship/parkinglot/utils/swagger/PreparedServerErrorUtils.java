package com.endava.md.internship.parkinglot.utils.swagger;

public class PreparedServerErrorUtils {

    private PreparedServerErrorUtils() {
    }

    public static final String PREPARED_SERVER_ERROR_RESPONSE = """
            {
                "success": false,
                "error": [
                    5001
                ]
            }""";

}
