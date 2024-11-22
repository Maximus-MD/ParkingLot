package com.endava.md.internship.parkinglot.utils;

import com.endava.md.internship.parkinglot.dto.RoleSwitchResponseDto;

public class RoleSwitchDTOUtils {
    public static RoleSwitchResponseDto getPreparedForAdminResponseDTO() {
        return new RoleSwitchResponseDto("AlexTests@gmail.com", true, "ROLE_ADMIN");
    }

    public static RoleSwitchResponseDto getPreparedForRegularResponseDTO() {
        return new RoleSwitchResponseDto("AlexTests@gmail.com", true, "ROLE_REGULAR");
    }
}
