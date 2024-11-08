package com.endava.md.internship.parkinglot.service.impl;

import com.endava.md.internship.parkinglot.dto.RegistrationRequestDto;

import com.endava.md.internship.parkinglot.dto.RegistrationResponseDto;
import com.endava.md.internship.parkinglot.exception.RegistrationException;
import com.endava.md.internship.parkinglot.model.User;
import com.endava.md.internship.parkinglot.repository.UserRepository;
import com.endava.md.internship.parkinglot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private void checkEmailAndPhoneAvailability(RegistrationRequestDto registrationDto) {
        if (userRepository.existsByEmail(registrationDto.email())) {
            throw new RegistrationException("Duplicate email", 3001);
        }
        if (userRepository.existsByPhone(registrationDto.phone())) {
            throw new RegistrationException("Duplicate phone", 3002);
        }
    }

    private User convertToUser(RegistrationRequestDto registrationDto) {
        return User.builder()
                .name(registrationDto.name())
                .email(registrationDto.email())
                .password(registrationDto.password())
                .phone(registrationDto.phone())
                .build();
    }

    @Override
    public RegistrationResponseDto registerNewUser(RegistrationRequestDto registrationDto) {
        checkEmailAndPhoneAvailability(registrationDto);
        User user = convertToUser(registrationDto);
        userRepository.save(user);

        return new RegistrationResponseDto(true, null, null);
    }
}