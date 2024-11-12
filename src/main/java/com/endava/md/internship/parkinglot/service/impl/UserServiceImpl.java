package com.endava.md.internship.parkinglot.service.impl;

import com.endava.md.internship.parkinglot.dto.RegistrationRequestDto;
import com.endava.md.internship.parkinglot.dto.RegistrationResponseDto;
import com.endava.md.internship.parkinglot.exception.RegistrationException;
import com.endava.md.internship.parkinglot.exception.RoleNotFoundException;
import com.endava.md.internship.parkinglot.model.Role;
import com.endava.md.internship.parkinglot.model.User;
import com.endava.md.internship.parkinglot.repository.RoleRepository;
import com.endava.md.internship.parkinglot.repository.UserRepository;
import com.endava.md.internship.parkinglot.security.JWTService;
import com.endava.md.internship.parkinglot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.endava.md.internship.parkinglot.model.RoleEnum.ROLE_REGULAR;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final JWTService jwtService;

    @Transactional
    @Override
    public RegistrationResponseDto registerNewUser(RegistrationRequestDto registrationDto) {
        checkEmailAndPhoneAvailability(registrationDto);
        User user = convertToUser(registrationDto);
        userRepository.save(user);

        String generateToken = jwtService.generateToken(registrationDto.email());
        return new RegistrationResponseDto(true, generateToken, null);
    }

    private void checkEmailAndPhoneAvailability(RegistrationRequestDto registrationDto) {
        if (userRepository.existsByEmail(registrationDto.email())) {
            throw new RegistrationException("Duplicate email", 3001);
        }
        if (userRepository.existsByPhone(registrationDto.phone())) {
            throw new RegistrationException("Duplicate phone", 3002);
        }
    }

    private Role getDefaultRole() {
        return roleRepository.findByName(ROLE_REGULAR)
                .orElseThrow(() -> new RoleNotFoundException("No role found"));
    }

    private User convertToUser(RegistrationRequestDto registrationDto) {
        Role role = getDefaultRole();

        return User.builder()
                .name(registrationDto.name())
                .email(registrationDto.email())
                .password(registrationDto.password())
                .phone(registrationDto.phone())
                .role(role)
                .build();
    }
}