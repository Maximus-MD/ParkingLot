package com.endava.md.internship.parkinglot.dto;

public record RegistrationRequestDto(
        String name,
        String email,
        String password,
        String phone
) {
}
