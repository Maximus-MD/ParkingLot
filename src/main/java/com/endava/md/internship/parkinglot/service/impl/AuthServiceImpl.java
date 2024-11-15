package com.endava.md.internship.parkinglot.service.impl;

import com.endava.md.internship.parkinglot.dto.LoginRequestDto;
import com.endava.md.internship.parkinglot.dto.LoginResponseDto;
import com.endava.md.internship.parkinglot.exception.CustomAuthException;
import com.endava.md.internship.parkinglot.repository.UserRepository;
import com.endava.md.internship.parkinglot.security.JWTService;
import com.endava.md.internship.parkinglot.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.endava.md.internship.parkinglot.exception.AuthErrorTypeEnum.BAD_CREDENTIALS;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;

    private final JWTService jwtService;

    private final PasswordEncoder passwordEncoder;

    @Override
    public LoginResponseDto login(final LoginRequestDto loginRequestDto) {
        String emailDTO = loginRequestDto.email();
        String passwordDTO = loginRequestDto.password();

        userRepository.findByEmail(emailDTO).stream()
                .peek(userEntity -> validateIfPasswordsMatches(userEntity.getPassword(), passwordDTO))
                .findAny()
                .orElseThrow(() -> new CustomAuthException(BAD_CREDENTIALS,
                        String.format("User email: %s not found", emailDTO)));

        String token = jwtService.generateToken(emailDTO);

        return new LoginResponseDto(true, token, null);
    }

    private void validateIfPasswordsMatches(String entityPassword, String passwordDTO) {
        if (!passwordEncoder.matches(passwordDTO, entityPassword)) {
            throw new CustomAuthException(BAD_CREDENTIALS,
                    String.format("Password invalid: %s", passwordDTO));
        }
    }
}
