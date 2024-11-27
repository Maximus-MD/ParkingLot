package com.endava.md.internship.parkinglot.service;

import com.endava.md.internship.parkinglot.dto.LoginRequestDto;

public interface AuthService {
    String login(final LoginRequestDto loginRequestDto);
}
