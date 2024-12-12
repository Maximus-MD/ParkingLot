package com.endava.md.internship.parkinglot.utils.swagger;

public class PreparedResponseMessageDto {

    private PreparedResponseMessageDto() {
    }

    public static final String PREPARED_SUCCESS_RESPONSE_MESSAGE_DTO_WITH_ADMIN_ROLE = """
            {
                "success": true,
                "email": "jo12hn1992@gmail.com",
                "result": "ROLE_ADMIN"
            }
            """;

    public static final String PREPARED_SUCCESS_RESPONSE_MESSAGE_DTO_WITH_REGULAR_ROLE = """
            {
                "success": true,
                "email": "jo12hn1992@gmail.com",
                "result": "ROLE_REGULAR"
            }
            """;

    public static final String PREPARED_SUCCESS_RESPONSE_MESSAGE_DTO = """
            {
                "success": true,
                "email": "jo12hn1992@gmail.com",
                "result": "Restored"
            }
            """;

}
