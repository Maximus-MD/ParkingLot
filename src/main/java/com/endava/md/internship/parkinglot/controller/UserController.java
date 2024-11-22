package com.endava.md.internship.parkinglot.controller;

import com.endava.md.internship.parkinglot.dto.RoleSwitchResponseDto;
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
    protected ResponseEntity<RoleSwitchResponseDto> switchUserRoleToAdmin(@Valid @RequestBody UserEmailDTO userEmail) {
        RoleSwitchResponseDto responseDto = userService.setNewRole(userEmail.email, ROLE_ADMIN);
        return ResponseEntity.ok(responseDto);
    }

    @PatchMapping("/switch-role/regular")
    protected ResponseEntity<RoleSwitchResponseDto> switchUserRoleToRegular(@Valid @RequestBody UserEmailDTO userEmail) {
        RoleSwitchResponseDto responseDto = userService.setNewRole(userEmail.email, ROLE_REGULAR);
        return ResponseEntity.ok(responseDto);
    }

    protected record UserEmailDTO(
            @NotBlank(message = "{message.invalid-email}")
            @Email(message = "{message.invalid-email}")
            String email
    ) {
    }
}
