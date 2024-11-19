package com.endava.md.internship.parkinglot.service;

import com.endava.md.internship.parkinglot.dto.RegistrationRequestDto;
import com.endava.md.internship.parkinglot.dto.RegistrationResponseDto;
import com.endava.md.internship.parkinglot.dto.RoleSwitchResponseDto;
import com.endava.md.internship.parkinglot.model.RoleEnum;

public interface UserService {
    RegistrationResponseDto registerNewUser(RegistrationRequestDto registrationDto);
    RoleSwitchResponseDto setNewRole(String email, RoleEnum role);
}
