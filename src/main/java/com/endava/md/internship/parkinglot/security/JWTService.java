package com.endava.md.internship.parkinglot.security;

import com.endava.md.internship.parkinglot.exception.CustomAuthException;
import com.endava.md.internship.parkinglot.model.User;
import com.endava.md.internship.parkinglot.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.endava.md.internship.parkinglot.exception.AuthErrorTypeEnum.USER_NOT_FOUND;

@Service
@AllArgsConstructor
public class JWTService {

    private final UserRepository userRepository;

    private final JWTUtils jwtUtils;

    public String generateToken(final String email) {
        return Optional.ofNullable(email)
                .filter(userRepository::existsByEmail)
                .map(jwtUtils::generateAccessToken)
                .orElseThrow(() ->
                        new CustomAuthException(USER_NOT_FOUND, String.format("User email: %s not found", email)));
    }

    protected User findUserByEmail(final String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new CustomAuthException(USER_NOT_FOUND, String.format("User email: %s not found", email)));
    }
}