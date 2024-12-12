package com.endava.md.internship.parkinglot.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.Set;

@Getter
public class ResponseDTO {
    @Schema(description = "Indicates whether the request was successful", example = "true")
    private boolean success;

    @Schema(description = "The authentication token returned in case of a successful request",
            example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHBpcmVkVXNlck5hbWUiOiJKb2huRG9lIn0.IwHtJkZX21h1eqkNR27DkOCTkUskZ8fmXrHXT4pyReA")
    private String token;

    @Schema(description = "A set of error codes if the request failed. Contains a list of error codes",
            example = "[2004, 3001]")
    private Set<Integer> error;

    public ResponseDTO(boolean success, String token, Set<Integer> error) {
        this.success = success;
        this.token = token;
        this.error = error;
    }
}