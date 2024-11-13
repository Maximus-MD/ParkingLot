package com.endava.md.internship.parkinglot.service;

import com.endava.md.internship.parkinglot.dto.LoginRequestDto;
import com.endava.md.internship.parkinglot.dto.LoginResponseDto;

public interface AuthService {
    LoginResponseDto login(final LoginRequestDto loginRequestDto);
}
