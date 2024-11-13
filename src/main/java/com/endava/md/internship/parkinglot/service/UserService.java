package com.endava.md.internship.parkinglot.service;

import com.endava.md.internship.parkinglot.dto.RegistrationRequestDto;
import com.endava.md.internship.parkinglot.dto.RegistrationResponseDto;

public interface UserService {
    RegistrationResponseDto registerNewUser(RegistrationRequestDto registrationDto);
    void switchRole(Long userId);
}
