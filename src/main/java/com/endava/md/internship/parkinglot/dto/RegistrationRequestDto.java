package com.endava.md.internship.parkinglot.dto;

<<<<<<< HEAD
public record RegistrationRequestDto(
        String name,
        String email,
        String password,
        String phone
) {
}
=======
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
>>>>>>> 02a431b (INTP0002PD-5633 Created RegistrationRequestDto and updated pom.xml)
