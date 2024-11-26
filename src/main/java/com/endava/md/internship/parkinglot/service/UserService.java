package com.endava.md.internship.parkinglot.service;

import com.endava.md.internship.parkinglot.dto.RegistrationRequestDto;
import com.endava.md.internship.parkinglot.dto.ResponseMessageDTO;
import com.endava.md.internship.parkinglot.model.RoleEnum;

public interface UserService {
    String registerNewUser(RegistrationRequestDto registrationDto);

    ResponseMessageDTO setNewRole(String email, RoleEnum role);
}
