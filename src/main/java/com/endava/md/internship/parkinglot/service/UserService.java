package com.endava.md.internship.parkinglot.service;

import com.endava.md.internship.parkinglot.dto.RegistrationRequestDto;

public interface UserService {
    void registerNewUser(RegistrationRequestDto registrationDto) throws Exception;
}
