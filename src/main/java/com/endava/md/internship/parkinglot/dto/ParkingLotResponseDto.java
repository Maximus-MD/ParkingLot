package com.endava.md.internship.parkinglot.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.Set;

@Builder
public record ParkingLotResponseDto(
        @Schema(description = "Indicates whether the request was successful", example = "true")
        boolean success,

        @Schema(description = "A set of error codes if the request failed. Contains a list of error codes",
                example = "[3001, 2005]")
        Set<Integer> error
) {
}