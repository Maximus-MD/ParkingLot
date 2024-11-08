package com.endava.md.internship.parkinglot.exception;

import com.endava.md.internship.parkinglot.dto.RegistrationResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Set;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        response.setHeader("Content-Type", "application/json");
        response.sendError(HttpServletResponse.SC_OK, String.valueOf(new RegistrationResponseDto(
                false, null, Set.of(Integer.valueOf(authException.getMessage())))));
    }
}
