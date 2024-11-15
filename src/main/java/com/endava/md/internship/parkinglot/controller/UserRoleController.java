package com.endava.md.internship.parkinglot.controller;

import com.endava.md.internship.parkinglot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/users")
@RestController
@RequiredArgsConstructor
public class UserRoleController {
    private final UserService userService;

    @PatchMapping("/switch-role/{email}")
    public ResponseEntity<String> switchUserRole(@PathVariable String email){
        userService.setNewRole(email);
        return ResponseEntity.ok("Role was switched.");
    }
}
