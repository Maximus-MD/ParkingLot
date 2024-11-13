package com.endava.md.internship.parkinglot.service.impl;

import com.endava.md.internship.parkinglot.dto.RegistrationRequestDto;
import com.endava.md.internship.parkinglot.dto.RegistrationResponseDto;
import com.endava.md.internship.parkinglot.exception.RegistrationException;
import com.endava.md.internship.parkinglot.exception.RoleNotFoundException;
import com.endava.md.internship.parkinglot.exception.UserNotFoundException;
import com.endava.md.internship.parkinglot.model.Role;
import com.endava.md.internship.parkinglot.model.User;
import com.endava.md.internship.parkinglot.repository.RoleRepository;
import com.endava.md.internship.parkinglot.repository.UserRepository;
import com.endava.md.internship.parkinglot.security.JWTService;
import com.endava.md.internship.parkinglot.service.EmailService;
import com.endava.md.internship.parkinglot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.endava.md.internship.parkinglot.model.RoleEnum.ROLE_ADMIN;
import static com.endava.md.internship.parkinglot.model.RoleEnum.ROLE_REGULAR;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final JWTService jwtService;
    private final EmailService emailService;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public RegistrationResponseDto registerNewUser(RegistrationRequestDto registrationDto) {
        checkEmailAndPhoneAvailability(registrationDto);
        User user = convertToUser(registrationDto);
        userRepository.save(user);

        String generateToken = jwtService.generateToken(registrationDto.email());
        return new RegistrationResponseDto(true, generateToken, null);
    }

    public void switchRole(Long userId) {
        String mailTo = "maximilian.stati@endava.com";

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User " + userId + " not found."));

        Role currentRole = user.getRole();
        Role newRole = getNewRole(currentRole);

        user.setRole(newRole);
        userRepository.save(user);

        emailService.sendEmail(mailTo, newRole.getName().name());
    }

    private Role getNewRole(Role currentRole) {
        if (currentRole.getName().equals(ROLE_REGULAR)) {
            return roleRepository.findByName(ROLE_ADMIN).orElseThrow(
                    () -> new RoleNotFoundException("Role " + ROLE_ADMIN + " not found."));
        } else if (currentRole.getName().equals(ROLE_ADMIN)) {
            return roleRepository.findByName(ROLE_REGULAR).orElseThrow(
                    () -> new RoleNotFoundException("Role " + ROLE_ADMIN + " not found."));
        } else {
            throw new RoleNotFoundException("Unknown role: " + currentRole.getName() + " not found.");
        }
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
                .password(passwordEncoder.encode(registrationDto.password()))
                .phone(registrationDto.phone())
                .role(role)
                .build();
    }
}