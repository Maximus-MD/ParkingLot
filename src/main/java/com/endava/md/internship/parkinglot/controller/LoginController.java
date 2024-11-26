package com.endava.md.internship.parkinglot.controller;

import com.endava.md.internship.parkinglot.dto.LoginRequestDto;
import com.endava.md.internship.parkinglot.dto.ResponseDTO;
import com.endava.md.internship.parkinglot.utils.ResponseFactory;
import com.endava.md.internship.parkinglot.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/login")
public class LoginController {

    private final AuthService authService;

    @PostMapping
    public ResponseEntity<ResponseDTO> login(@Valid @RequestBody final LoginRequestDto loginRequestDto) {
        return ResponseFactory.createResponse(authService.login(loginRequestDto));
    }
}
