package com.endava.md.internship.parkinglot.service.impl;

import com.endava.md.internship.parkinglot.dto.RegistrationRequestDto;
import com.endava.md.internship.parkinglot.dto.RegistrationResponseDto;
import com.endava.md.internship.parkinglot.exception.CustomAuthException;
import com.endava.md.internship.parkinglot.exception.CustomEmailSendException;
import com.endava.md.internship.parkinglot.exception.RegistrationException;
import com.endava.md.internship.parkinglot.exception.RoleNotFoundException;
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

import static com.endava.md.internship.parkinglot.exception.AuthErrorTypeEnum.USER_NOT_FOUND;
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

    public void setNewRole(final String email) {
        final String mailTo = "maximilian.stati@endava.com";
        final String subject = "Role change notification.";
        final String DEPRIVED = "You have been deprived of Admin role for Parking Lot app.";
        final String GRANTED = "You have been granted an Admin role for Parking Lot app.";

        userRepository.findByEmail(email).stream()
                .peek(userEntity -> {
                    Role role = userEntity.getRole();
                    userEntity.setRole(switchRole(role));
                    userRepository.save(userEntity);

                    String messageText = userEntity.getRole().getRoleName().name()
                            .equals(ROLE_REGULAR.name()) ? DEPRIVED : GRANTED;
                    try {
                        emailService.sendEmail(mailTo, subject, messageText);
                    } catch (Exception e) {
                        throw new CustomEmailSendException("Failed to send email to " + mailTo);
                    }
                })
                .findAny()
                .orElseThrow(() -> new CustomAuthException(USER_NOT_FOUND,
                        String.format("User email: %s not found", email)));
    }


    private Role switchRole(Role role) {
        switch (role.getRoleName()) {
            case ROLE_ADMIN -> role.setRoleName(ROLE_REGULAR);

            case ROLE_REGULAR -> role.setRoleName(ROLE_ADMIN);
        }
        return role;
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
        return roleRepository.findByRoleName(ROLE_REGULAR)
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