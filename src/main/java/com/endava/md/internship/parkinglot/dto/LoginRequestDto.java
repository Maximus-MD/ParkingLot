package com.endava.md.internship.parkinglot.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record LoginRequestDto(
        @NotBlank(message = "{message.invalid-email}")
        @Pattern(regexp = "^[a-zA-Z0-9]+(?:[._][a-zA-Z0-9]+)*@[a-zA-Z0-9]+(?:[.-][a-zA-Z0-9]+)*\\.[a-zA-Z]{2,}$", message ="{message.invalid-email}")
        String email,

        @NotBlank(message = "{message.weak-password}")
        @Size(min = 5, max = 10, message = "{message.weak-password}")
        @Pattern(regexp = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).*", message = "{message.weak-password}")
        String password
) {
}
