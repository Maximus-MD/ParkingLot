package com.endava.md.internship.parkinglot.service.impl;

import com.endava.md.internship.parkinglot.dto.LoginRequestDto;
import com.endava.md.internship.parkinglot.dto.LoginResponseDto;
import com.endava.md.internship.parkinglot.exception.CustomAuthException;
import com.endava.md.internship.parkinglot.model.User;
import com.endava.md.internship.parkinglot.repository.UserRepository;
import com.endava.md.internship.parkinglot.security.JWTService;
import com.endava.md.internship.parkinglot.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.endava.md.internship.parkinglot.exception.AuthErrorTypeEnum.BAD_CREDENTIALS;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    private final JWTService jwtService;

    private final PasswordEncoder passwordEncoder;

    @Override
    public LoginResponseDto login(final LoginRequestDto loginRequestDto) {
        String emailDTO = loginRequestDto.email();
        String passwordDTO = loginRequestDto.password();

        validateCredentials(emailDTO, passwordDTO);

        String token = jwtService.generateToken(emailDTO);

        return new LoginResponseDto(true, token, null);
    }

    private void validateCredentials(String email, String password) {
        User user = userRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new CustomAuthException(BAD_CREDENTIALS,
                        String.format("User with email '%s' not found", email)));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new CustomAuthException(BAD_CREDENTIALS, "Invalid password");
        }
    }
}
