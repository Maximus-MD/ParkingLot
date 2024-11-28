package com.endava.md.internship.parkinglot.dto;

import lombok.Getter;

import java.util.Set;

@Getter
public class ResponseGenericErrorDTO {
    private final boolean success;
    private final Set<Integer> error;

    public ResponseGenericErrorDTO(boolean success, Set<Integer> error) {
        this.success = success;
        this.error = error;
    }
}
