package com.endava.md.internship.parkinglot.controller;

import com.endava.md.internship.parkinglot.utils.ResponseFactory;
import com.endava.md.internship.parkinglot.dto.ResponseMessageDTO;
import com.endava.md.internship.parkinglot.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.endava.md.internship.parkinglot.model.RoleEnum.ROLE_ADMIN;
import static com.endava.md.internship.parkinglot.model.RoleEnum.ROLE_REGULAR;

@RequestMapping("/users")
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PatchMapping("/switch-role/admin")
    protected ResponseEntity<ResponseMessageDTO> switchUserRoleToAdmin(@Valid @RequestBody UserEmailDTO userEmail) {
        return ResponseFactory.createResponse(userService.setNewRole(userEmail.email, ROLE_ADMIN));
    }

    @PatchMapping("/switch-role/regular")
    protected ResponseEntity<ResponseMessageDTO> switchUserRoleToRegular(@Valid @RequestBody UserEmailDTO userEmail) {
        return ResponseFactory.createResponse(userService.setNewRole(userEmail.email, ROLE_REGULAR));
    }

    protected record UserEmailDTO(
            @NotBlank(message = "{message.invalid-email}")
            @Email(message = "{message.invalid-email}")
            String email
    ) {
    }
}