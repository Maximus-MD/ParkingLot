package com.endava.md.internship.parkinglot.utils;

import com.endava.md.internship.parkinglot.dto.LoginRequestDto;
import com.endava.md.internship.parkinglot.dto.LoginResponseDto;

public class LoginDTOUtils {
    public static LoginRequestDto getPreparedRequestDto() {
        return new LoginRequestDto("Alex@gmail.com", "b^nlM71");
    }

    public static LoginResponseDto getPreparedResponseDto() {
        return new LoginResponseDto(true, TokenUtils.getPreparedToken(), null);
    }
}
