package com.endava.md.internship.parkinglot.utils;

import com.endava.md.internship.parkinglot.dto.ResponseMessageDTO;

public class RoleSwitchDTOUtils {
    public static ResponseMessageDTO getPreparedForAdminResponseDTO() {
        return new ResponseMessageDTO(true, "AlexTests@gmail.com", "ROLE_ADMIN");
    }

    public static ResponseMessageDTO getPreparedForRegularResponseDTO() {
        return new ResponseMessageDTO(true, "AlexTests@gmail.com", "ROLE_REGULAR");
    }
}
