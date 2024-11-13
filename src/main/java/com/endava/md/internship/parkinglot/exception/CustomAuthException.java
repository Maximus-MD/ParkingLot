package com.endava.md.internship.parkinglot.exception;

import lombok.Getter;
import org.springframework.security.access.AccessDeniedException;

@Getter
public class CustomAuthException extends AccessDeniedException {
    private final AuthErrorTypeEnum authErrorType;

    public CustomAuthException(AuthErrorTypeEnum authErrorType, String message) {
        super(message);
        this.authErrorType = authErrorType;
    }
}