package com.endava.md.internship.parkinglot.dto;

import lombok.Getter;

import java.util.Set;

@Getter
public class ResponseDTO {
    private boolean success;
    private String token;
    private Set<Integer> error;

    public ResponseDTO(boolean success, String token, Set<Integer> error) {
        this.success = success;
        this.token = token;
        this.error = error;
    }
}