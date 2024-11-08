package com.endava.md.internship.parkinglot.dto;

import java.util.Set;

public record RegistrationResponseDto(Boolean success, String token, Set<Integer> error) {
}
