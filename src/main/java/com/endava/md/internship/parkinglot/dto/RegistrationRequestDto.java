package com.endava.md.internship.parkinglot.dto;

import com.endava.md.internship.parkinglot.validation.annotation.UniqueEmail;
import com.endava.md.internship.parkinglot.validation.annotation.UniquePhone;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegistrationRequestDto(

        @NotBlank(message = "{message.invalid-name}")
        @Size(min = 1, max = 30, message = "{message.invalid-name}")
        @Pattern(regexp = "^[a-zA-Z]+$", message = "{message.invalid-name}")
        String name,

        @NotBlank(message = "{message.invalid-email}")
        @Pattern(regexp = "^[a-zA-Z0-9]+(?:\\.[a-zA-Z0-9]+){0,30}@[a-zA-Z0-9]+(?:\\.[a-zA-Z0-9]+){0,20}\\.[a-zA-Z]{2,}$", message = "{message.invalid-email}")
        @UniqueEmail
        String email,

        @NotBlank(message = "{message.invalid-password}")
        @Size(min = 5, max = 10, message = "{message.invalid-password}")
        @Pattern(regexp = "^(?=.*\\d)(?=.*[A-Z])(?=.*[^\\w\\s])(?=\\S+$).*", message = "{message.invalid-password}")
        String password,

        @NotBlank(message = "{message.invalid-phone}")
        @Pattern(regexp = "^0[67]\\d{7}$", message = "{message.invalid-phone}")
        @UniquePhone
        String phone
) {
}
