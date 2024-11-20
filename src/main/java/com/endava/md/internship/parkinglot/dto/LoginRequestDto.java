package com.endava.md.internship.parkinglot.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record LoginRequestDto(
        @NotBlank(message = "{message.invalid-email}")
        @Pattern(regexp = "^[a-zA-Z0-9]+(?:\\.[a-zA-Z0-9]+){0,30}@[a-zA-Z0-9]+(?:\\.[a-zA-Z0-9]+){0,20}\\.[a-zA-Z]{2,}$", message = "{message.invalid-email}")
        String email,

        @NotBlank(message = "{message.invalid-password}")
        @Size(min = 5, max = 10, message = "{message.invalid-password}")
        @Pattern(regexp = "^(?=.*\\d)(?=.*[A-Z])(?=.*[^\\w\\s])(?=\\S+$).*", message = "{message.invalid-password}")
        String password
) {
}