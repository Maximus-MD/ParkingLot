package com.endava.md.internship.parkinglot.controller;

import com.endava.md.internship.parkinglot.dto.RegistrationRequestDto;
import com.endava.md.internship.parkinglot.dto.RegistrationResponseDto;
import com.endava.md.internship.parkinglot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/register")
public class RegistrationController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<RegistrationResponseDto> registerUser(@RequestBody RegistrationRequestDto registrationDto) {
      RegistrationResponseDto response = userService.registerNewUser(registrationDto);
      return ResponseEntity.ok(response);
    }
}


