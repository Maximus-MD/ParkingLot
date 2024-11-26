package com.endava.md.internship.parkinglot.utils;

import com.endava.md.internship.parkinglot.dto.LoginRequestDto;
import com.endava.md.internship.parkinglot.dto.ResponseDTO;
import com.endava.md.internship.parkinglot.dto.ResponseMessageDTO;

public class ResponseDTOUtils {
    public static LoginRequestDto getPreparedRequestDto() {
        return new LoginRequestDto("Alex@gmail.com", "b^nlM71");
    }

    public static ResponseDTO getPreparedResponseDTO() {
        return new ResponseDTO(true, TokenUtils.getPreparedToken(), null);
    }

    public static ResponseMessageDTO getPreparedResponseMessageDTO() {
        return new ResponseMessageDTO(true, TokenUtils.getPreparedToken(), null);
    }

    public static ResponseMessageDTO getPreparedResponseMessageForRoleDTO(String email, String role) {
        return new ResponseMessageDTO(true, email, role);
    }
}

