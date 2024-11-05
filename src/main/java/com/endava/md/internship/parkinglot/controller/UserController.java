package com.endava.md.internship.parkinglot.controller;

import com.endava.md.internship.parkinglot.dto.RegistrationRequestDto;
import com.endava.md.internship.parkinglot.exceptions.RegistrationException;
import com.endava.md.internship.parkinglot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Collections;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> registerUser(@RequestBody RegistrationRequestDto registrationDto) {
        try {
            userService.registerNewUser(registrationDto);
            return ResponseEntity.ok(Map.of("success", true));
        } catch (RegistrationException e) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("success", false, "error", Collections.singletonList(e.getErrorCode())));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("success", false, "error", Collections.singletonList(5001)));
        }
    }

}


