package com.endava.md.internship.parkinglot.exception;

import com.endava.md.internship.parkinglot.dto.RegistrationResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Set;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {
        response.setHeader("Content-Type", "application/json");
        response.sendError(HttpServletResponse.SC_OK, String.valueOf(new RegistrationResponseDto(
                false, null, Set.of(Integer.valueOf(accessDeniedException.getMessage())))));
    }
}

