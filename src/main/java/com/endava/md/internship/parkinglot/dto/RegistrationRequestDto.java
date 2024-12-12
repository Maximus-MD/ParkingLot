package com.endava.md.internship.parkinglot.dto;

import com.endava.md.internship.parkinglot.validation.annotation.UniqueEmail;
import com.endava.md.internship.parkinglot.validation.annotation.UniquePhone;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegistrationRequestDto(
        @NotBlank(message = "{message.invalid-name}")
        @Size(min = 1, max = 30, message = "{message.invalid-name}")
        @Pattern(regexp = "^[a-zA-Z]+$", message = "{message.invalid-name}")
        @Schema(description = "The full name of the user. It must be between 1 and 30 characters and contain only alphabetic characters.",
                example = "JohnDoe")
        String name,

        @NotBlank(message = "{message.invalid-email}")
        @Pattern(regexp = "^[a-zA-Z0-9]+(?:\\.[a-zA-Z0-9]+){0,30}@[a-zA-Z0-9]+(?:\\.[a-zA-Z0-9]+){0,20}\\.[a-zA-Z]{2,}$", message = "{message.invalid-email}")
        @UniqueEmail
        @Schema(description = "The email address of the user. It must follow a valid email format and be unique.",
                example = "johndoe@example.com")
        String email,

        @NotBlank(message = "{message.invalid-password}")
        @Size(min = 5, max = 10, message = "{message.invalid-password}")
        @Pattern(regexp = "^(?=.*\\d)(?=.*[A-Z])(?=.*[^\\w\\s])(?=\\S+$).*", message = "{message.invalid-password}")
        @Schema(description = "The password for the user's account. It must be between 5 and 10 characters and contain at least one digit, one uppercase letter, and one special character.",
                example = "A1b@X7z&")
        String password,

        @NotBlank(message = "{message.invalid-phone}")
        @Pattern(regexp = "^0[67]\\d{7}$", message = "{message.invalid-phone}")
        @UniquePhone
        @Schema(description = "The phone number of the user. It must match the pattern for a valid phone number starting with 06 or 07, followed by 7 digits.",
                example = "069234567")
        String phone
) {
}
