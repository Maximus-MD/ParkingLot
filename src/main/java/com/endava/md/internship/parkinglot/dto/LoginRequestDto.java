package com.endava.md.internship.parkinglot.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record LoginRequestDto(
        @NotBlank(message = "{message.invalid-email}")
        @Pattern(regexp = "^[a-zA-Z0-9]+(?:\\.[a-zA-Z0-9]+){0,30}@[a-zA-Z0-9]+(?:\\.[a-zA-Z0-9]+){0,20}\\.[a-zA-Z]{2,}$", message = "{message.invalid-email}")
        @Schema(description = "The email address used for login. It must follow a valid email format.",
                example = "johndoe@example.com")
        String email,

        @NotBlank(message = "{message.invalid-password}")
        @Size(min = 5, max = 10, message = "{message.invalid-password}")
        @Pattern(regexp = "^(?=.*\\d)(?=.*[A-Z])(?=.*[^\\w\\s])(?=\\S+$).*", message = "{message.invalid-password}")
        @Schema(description = "The password associated with the account. It must be between 5 and 10 characters long and contain at least one digit, one uppercase letter, and one special character.",
                example = "A1b@X7z&")
        String password
) {
}