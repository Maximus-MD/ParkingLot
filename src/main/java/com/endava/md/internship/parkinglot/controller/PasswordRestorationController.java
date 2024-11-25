package com.endava.md.internship.parkinglot.controller;

import com.endava.md.internship.parkinglot.service.PasswordRestorationService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/restore-password")
public class PasswordRestorationController {

    private final PasswordRestorationService passwordRestorationService;

    public PasswordRestorationController(PasswordRestorationService passwordRestorationService) {
        this.passwordRestorationService = passwordRestorationService;
    }

    @PatchMapping
    protected ResponseEntity<SuccessfulResponseDTO> passwordRestoration(@Valid @RequestBody UserEmailDTO userEmail) throws MessagingException {
        passwordRestorationService.restorePassword(userEmail.email);
        return ResponseEntity.ok(new SuccessfulResponseDTO("success"));
    }

    protected record UserEmailDTO(
            @NotBlank(message = "{message.invalid-email}")
            @Email(message = "{message.invalid-email}")
            String email
    ) {
    }

    protected record SuccessfulResponseDTO(
            String response
    ) {
    }
}
