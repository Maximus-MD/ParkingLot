package com.endava.md.internship.parkinglot.service.impl;

import com.endava.md.internship.parkinglot.dto.RegistrationRequestDto;
import com.endava.md.internship.parkinglot.exceptions.RegistrationException;
import com.endava.md.internship.parkinglot.model.User;
import com.endava.md.internship.parkinglot.repository.UserRepository;
import com.endava.md.internship.parkinglot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;

    @Override
    public void registerNewUser(RegistrationRequestDto registrationDto) {
        if (userRepository.existsByEmail(registrationDto.getEmail())) {
            throw new RegistrationException("Duplicate email", 3001);
        }
        if (userRepository.existsByPhone(registrationDto.getPhone())) {
            throw new RegistrationException("Duplicate phone", 3002);
        }

        User user = new User();
        user.setName(registrationDto.getName());
        user.setEmail(registrationDto.getEmail());
        user.setPassword(registrationDto.getPassword());
        user.setPhone(registrationDto.getPhone());

        userRepository.save(user);
    }
}