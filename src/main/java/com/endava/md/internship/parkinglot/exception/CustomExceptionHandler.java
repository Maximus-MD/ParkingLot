package com.endava.md.internship.parkinglot.exception;

import com.endava.md.internship.parkinglot.dto.LoginResponseDto;
import com.endava.md.internship.parkinglot.dto.RegistrationResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
@PropertySource("classpath:validation-errors.properties")
public class CustomExceptionHandler {

    @Value("${message.jwt-generation-error}")
    int JWT_TOKEN_GENERATION_ERROR;

    @Value("${message.bad.credentials}")
    int BAD_CREDENTIALS;

    @Value("${message.user-not-found}")
    int USER_NOT_FOUND;

    @Value("${message.invalid-jwt}")
    int INVALID_JWT;

    @Value("${message.server-error}")
    int SERVER_ERROR;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RegistrationResponseDto> handleValidationExceptions(MethodArgumentNotValidException exception) {
        Set<String> errors = new HashSet<>();
        exception.getBindingResult().getAllErrors().forEach(error -> errors.add(error.getDefaultMessage()));
        Set<Integer> resultErrors = errors.stream().map(Integer::parseInt).collect(Collectors.toSet());
        RegistrationResponseDto responseDto = new RegistrationResponseDto(false, null, resultErrors);
        return ResponseEntity.ok(responseDto);
    }

    @ExceptionHandler(CustomAuthException.class)
    public ResponseEntity<LoginResponseDto> handleCustomAuthException(final CustomAuthException authException) {
        log.error(authException.getMessage());
        return switch (authException.getAuthErrorType()) {
            case JWT_TOKEN_GENERATION_ERROR -> buildResponseEntity(JWT_TOKEN_GENERATION_ERROR);

            case BAD_CREDENTIALS -> buildResponseEntity(BAD_CREDENTIALS);

            case USER_NOT_FOUND -> buildResponseEntity(USER_NOT_FOUND);

            case INVALID_JWT -> buildResponseEntity(INVALID_JWT);
        };
    }

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<RegistrationResponseDto> handleRoleNotFoundException() {
        RegistrationResponseDto responseDto = new RegistrationResponseDto(false, null,
                Set.of(SERVER_ERROR));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDto);
    }

    @ExceptionHandler(EmailSendException.class)
    public ResponseEntity<RegistrationResponseDto> handleEmailSendException() {
        RegistrationResponseDto responseDto = new RegistrationResponseDto(false, null,
                Set.of(SERVER_ERROR));

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDto);
    }

    @ExceptionHandler(RegistrationException.class)
    public ResponseEntity<RegistrationResponseDto> handleRegistrationException(RegistrationException exception) {
        log.error("Registration error: {}", exception.getMessage(), exception);

        int errorCode = exception.getErrorCode();

        RegistrationResponseDto responseDto = new RegistrationResponseDto(false, null, Set.of(errorCode));
        return ResponseEntity.ok(responseDto);
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<RegistrationResponseDto> handleGenericError() {
        RegistrationResponseDto responseDto = new RegistrationResponseDto(false, null,
                Set.of(SERVER_ERROR));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDto);
    }

    private ResponseEntity<LoginResponseDto> buildResponseEntity(final Integer error) {
        LoginResponseDto loginResponseDto = new LoginResponseDto(false, null, Set.of(error));
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(loginResponseDto);
    }
}