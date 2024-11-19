package com.endava.md.internship.parkinglot.controller;

import com.endava.md.internship.parkinglot.dto.RoleSwitchResponseDto;
import com.endava.md.internship.parkinglot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.endava.md.internship.parkinglot.model.RoleEnum.ROLE_ADMIN;
import static com.endava.md.internship.parkinglot.model.RoleEnum.ROLE_REGULAR;

@RequestMapping("/users")
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PatchMapping("/switch-role/admin/{email}")
    public ResponseEntity<RoleSwitchResponseDto> switchUserRoleToAdmin(@PathVariable String email){
        RoleSwitchResponseDto responseDto = userService.setNewRole(email, ROLE_ADMIN);
        return ResponseEntity.ok(responseDto);
    }

    @PatchMapping("/switch-role/regular/{email}")
    public ResponseEntity<RoleSwitchResponseDto> switchUserRoleToRegular(@PathVariable String email){
        RoleSwitchResponseDto responseDto = userService.setNewRole(email, ROLE_REGULAR);
        return ResponseEntity.ok(responseDto);
    }
}
