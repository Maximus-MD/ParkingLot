package com.endava.md.internship.parkinglot.dto;

import lombok.Getter;

@Getter
public class ResponseMessageDTO {
    private boolean success;
    private String email;
    private String result;

    public ResponseMessageDTO(boolean success, String email, String result) {
        this.success = success;
        this.email = email;
        this.result = result;
    }
}
