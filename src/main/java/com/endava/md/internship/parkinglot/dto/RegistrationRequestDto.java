package com.endava.md.internship.parkinglot.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationRequestDto {

    private String name;
    private String email;
    private String password;
    private String phone;
}