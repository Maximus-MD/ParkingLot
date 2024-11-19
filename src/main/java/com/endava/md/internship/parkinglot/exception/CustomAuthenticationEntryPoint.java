package com.endava.md.internship.parkinglot.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Value("${message.request.authentication.failed}")
    int REQUEST_AUTHENTICATION_FAILED;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        log.error(authException.getMessage(), authException);

        response.setHeader("Content-Type", "application/json");
        response.setStatus(HttpServletResponse.SC_OK);
        String responseBody = String.format("""
                {
                    "success": false,
                    "token": null,
                    "error": [%s]
                }
                """, REQUEST_AUTHENTICATION_FAILED);
        response.getWriter().write(responseBody);
    }
}
