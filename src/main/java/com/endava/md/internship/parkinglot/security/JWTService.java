package com.endava.md.internship.parkinglot.security;

import com.endava.md.internship.parkinglot.exception.CustomAuthException;
import com.endava.md.internship.parkinglot.model.User;
import com.endava.md.internship.parkinglot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.endava.md.internship.parkinglot.exception.AuthErrorTypeEnum.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class JWTService {

    private final UserRepository userRepository;

    private final JWTUtils jwtUtils;

    public String generateToken(final String email) {
        return userRepository.findByEmailIgnoreCase(email)
                .map(userEntity -> {
                    String role = userEntity.getRole().getRoleName().name();
                    return jwtUtils.generateAccessToken(email, role);
                })
                .orElseThrow(() ->
                        new CustomAuthException(USER_NOT_FOUND, String.format("User email: %s not found", email)));
    }

    protected User findUserByEmail(final String email) {
        return userRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() ->
                        new CustomAuthException(USER_NOT_FOUND, String.format("User email: %s not found", email)));
    }
}