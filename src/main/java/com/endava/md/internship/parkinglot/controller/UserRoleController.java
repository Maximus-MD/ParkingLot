package com.endava.md.internship.parkinglot.controller;

import com.endava.md.internship.parkinglot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/users")
@RestController
@RequiredArgsConstructor
public class UserRoleController {
    private final UserService userService;

    @PutMapping("/{userId}/switch-role")
    public ResponseEntity<String> switchUserRole(@PathVariable Long userId){
        userService.switchRole(userId);
        return ResponseEntity.ok("Role was switched.");
    }
}
