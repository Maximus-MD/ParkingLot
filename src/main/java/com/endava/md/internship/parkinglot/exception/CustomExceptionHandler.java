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
    private int jwtTokenGenerationError;

    @Value("${message.bad.credentials}")
    private int badCredentials;

    @Value("${message.user-not-found}")
    private int userNotFound;

    @Value("${message.invalid-jwt}")
    private int invalidJwt;

    @Value("${message.server-error}")
    private int serverError;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RegistrationResponseDto> handleValidationExceptions(MethodArgumentNotValidException exception) {
        log.error("Validation exception", exception);
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
            case JWT_TOKEN_GENERATION_ERROR -> buildResponseEntity(jwtTokenGenerationError);

            case BAD_CREDENTIALS -> buildResponseEntity(badCredentials);

            case USER_NOT_FOUND -> buildResponseEntity(userNotFound);

            case INVALID_JWT -> buildResponseEntity(invalidJwt);
        };
    }

    @ExceptionHandler(RegistrationException.class)
    public ResponseEntity<RegistrationResponseDto> handleRegistrationException(RegistrationException exception) {
        log.error("Registration error: {}", exception.getMessage(), exception);

        int errorCode = exception.getErrorCode();

        RegistrationResponseDto responseDto = new RegistrationResponseDto(false, null, Set.of(errorCode));
        return ResponseEntity.ok(responseDto);
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<RegistrationResponseDto> handleGenericError(Throwable throwable) {
        StackTraceElement traceElement = throwable.getStackTrace()[0];
        log.error("Generic error in class: {}, method: {}, line: {}, the error: {}",
                traceElement.getClassName(), traceElement.getMethodName(),
                traceElement.getLineNumber(), throwable.getMessage(), throwable);

        RegistrationResponseDto responseDto = new RegistrationResponseDto(false, null,
                Set.of(serverError));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDto);
    }

    private ResponseEntity<LoginResponseDto> buildResponseEntity(final Integer error) {
        LoginResponseDto loginResponseDto = new LoginResponseDto(false, null, Set.of(error));
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(loginResponseDto);
    }
}