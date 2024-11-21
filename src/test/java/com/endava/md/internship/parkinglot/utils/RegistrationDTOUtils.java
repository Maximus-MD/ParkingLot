package com.endava.md.internship.parkinglot.utils;

import com.endava.md.internship.parkinglot.dto.RegistrationRequestDto;

public class RegistrationDTOUtils {
    public static RegistrationRequestDto getPreparedRequestDto() {
        return new RegistrationRequestDto("Alex", "AlexTests@gmail.com", "b^nlM71", "069775533");
    }
}
