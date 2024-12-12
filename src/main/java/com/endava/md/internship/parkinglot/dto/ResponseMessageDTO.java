package com.endava.md.internship.parkinglot.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class ResponseMessageDTO {
    @Schema(description = "Indicates whether the request was successful", example = "true")
    private boolean success;

    @Schema(description = "The email address associated with the response",
            example = "user@example.com")
    private String email;

    @Schema(description = "The result message or status of the request",
            example = "Message was successfully sent")
    private String result;

    public ResponseMessageDTO(boolean success, String email, String result) {
        this.success = success;
        this.email = email;
        this.result = result;
    }
}
