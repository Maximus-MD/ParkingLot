package com.endava.md.internship.parkinglot.dto;

import com.endava.md.internship.parkinglot.validation.annotation.UniqueEmail;
import com.endava.md.internship.parkinglot.validation.annotation.UniquePhone;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegistrationRequestDto(

        @NotBlank(message = "{message.invalid-name}")
        @Size(min = 1, max = 30, message = "{message.invalid-name}")
        @Pattern(regexp = "^[a-zA-Z]+$", message = "{message.invalid-name}")
        String name,

        @NotBlank(message ="{message.invalid-email}" )
        @Email(message = "{message.invalid-email}")
        @UniqueEmail
        String email,

        @NotBlank(message = "{message.weak-password}")
        @Size(min = 5, max = 10, message = "{message.weak-password}")
        @Pattern(regexp = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[^\\w\\d\\s])(?=\\S+$).*", message = "{message.weak-password}")
        String password,

        @NotBlank(message = "{message.invalid-phone}")
        @Pattern(regexp = "^\\d{9}$", message = "{message.invalid-phone}")
        @UniquePhone
        String phone
) {
}
