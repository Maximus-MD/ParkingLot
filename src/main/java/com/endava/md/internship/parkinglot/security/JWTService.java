package com.endava.md.internship.parkinglot.security;

import com.endava.md.internship.parkinglot.model.User;
import com.endava.md.internship.parkinglot.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class JWTService {

    private final UserRepository userRepository;

    private final JWTUtils jwtUtils;

    public String generateToken(final String email) {
        return Optional.ofNullable(email)
                .filter(userRepository::existsByEmail)
                .map(jwtUtils::generateAccessToken)
                .orElseThrow(() -> new UsernameNotFoundException("4033"));
    }

    protected User findUserByEmail(final String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException ("4033"));
    }
}
