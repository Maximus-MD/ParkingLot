package com.endava.md.internship.parkinglot.dto;

import java.util.Set;

public record LoginResponseDto(boolean success, String token, Set<Integer> error) {
}
