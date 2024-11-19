package com.endava.md.internship.parkinglot.service.impl;

import com.endava.md.internship.parkinglot.dto.RegistrationRequestDto;
import com.endava.md.internship.parkinglot.dto.RegistrationResponseDto;
import com.endava.md.internship.parkinglot.dto.RoleSwitchResponseDto;
import com.endava.md.internship.parkinglot.exception.CustomAuthException;
import com.endava.md.internship.parkinglot.exception.EmailSendException;
import com.endava.md.internship.parkinglot.exception.RegistrationException;
import com.endava.md.internship.parkinglot.exception.RoleNotFoundException;
import com.endava.md.internship.parkinglot.model.Role;
import com.endava.md.internship.parkinglot.model.RoleEnum;
import com.endava.md.internship.parkinglot.model.User;
import com.endava.md.internship.parkinglot.repository.RoleRepository;
import com.endava.md.internship.parkinglot.repository.UserRepository;
import com.endava.md.internship.parkinglot.security.JWTService;
import com.endava.md.internship.parkinglot.service.EmailSenderService;
import com.endava.md.internship.parkinglot.service.UserService;
import jakarta.mail.MessagingException;
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

    private final EmailSenderService emailService;

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

    @Transactional
    public RoleSwitchResponseDto setNewRole(final String email, final RoleEnum role) {
        final String DEPRIVED = "You have been deprived of Admin role for Parking Lot app.";
        final String GRANTED = "You have been granted an Admin role for Parking Lot app.";

        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new CustomAuthException(USER_NOT_FOUND, String.format("User with email %s not found", email)));

        if(user.getRole().getRoleName() == role){
            return new RoleSwitchResponseDto(email, true, user.getRole().getRoleName().name());
        }

        Role newRole = switchRole(user);
        user.setRole(newRole);

        String message = newRole.getRoleName().name()
                .equals(ROLE_REGULAR.name()) ? DEPRIVED : GRANTED;
        sendRoleChangeEmail(email, message);

        return new RoleSwitchResponseDto(email, true, newRole.getRoleName().name());
    }

    private void sendRoleChangeEmail(String mailTo, String message) {
        try {
            emailService.sendEmail(mailTo, "Role change notification.", message);
        } catch (MessagingException e) {
            throw new EmailSendException("Failed to send email to " + mailTo);
        }
    }

    @Transactional
    protected Role switchRole(User user) {
        if (user.getRole().getRoleName().equals(ROLE_REGULAR)) {
            user.setRole(getRoleByName(ROLE_ADMIN));
        } else {
            user.setRole(getRoleByName(ROLE_REGULAR));
        }

        return user.getRole();
    }

    private Role getRoleByName(RoleEnum roleName) {
        return roleRepository.findByRoleName(roleName)
                .orElseThrow(() -> new RoleNotFoundException(roleName.name()));
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