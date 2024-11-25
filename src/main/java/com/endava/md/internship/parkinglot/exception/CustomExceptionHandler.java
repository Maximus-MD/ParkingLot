package com.endava.md.internship.parkinglot.exception;

import com.endava.md.internship.parkinglot.dto.ResponseDTO;
import com.endava.md.internship.parkinglot.utils.ResponseFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

//@RestControllerAdvice
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
    public ResponseEntity<ResponseDTO> handleValidationExceptions(MethodArgumentNotValidException exception) {
        log.error("Validation exception", exception);
        Set<String> errors = new HashSet<>();
        exception.getBindingResult().getAllErrors().forEach(error -> errors.add(error.getDefaultMessage()));
        Set<Integer> resultErrors = errors.stream().map(Integer::parseInt).collect(Collectors.toSet());
        return ResponseFactory.createResponse(resultErrors);
    }

    @ExceptionHandler(CustomAuthException.class)
    public ResponseEntity<ResponseDTO> handleCustomAuthException(final CustomAuthException authException) {
        log.error(authException.getMessage());
        return switch (authException.getAuthErrorType()) {
            case JWT_TOKEN_GENERATION_ERROR -> ResponseFactory.createResponse(jwtTokenGenerationError);

            case BAD_CREDENTIALS -> ResponseFactory.createResponse(badCredentials);

            case USER_NOT_FOUND -> ResponseFactory.createResponse(userNotFound);

            case INVALID_JWT -> ResponseFactory.createResponse(invalidJwt);
        };
    }

    @ExceptionHandler(RegistrationException.class)
    public ResponseEntity<ResponseDTO> handleRegistrationException(RegistrationException exception) {
        log.error("Registration error: {}", exception.getMessage(), exception);
        return ResponseFactory.createResponse(Set.of(exception.getErrorCode()));
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ResponseDTO> handleGenericError(Throwable throwable) {
        StackTraceElement traceElement = throwable.getStackTrace()[0];
        log.error("Generic error in class: {}, method: {}, line: {}, the error: {}",
                traceElement.getClassName(), traceElement.getMethodName(),
                traceElement.getLineNumber(), throwable.getMessage(), throwable);

        return ResponseFactory.createResponse(serverError);
    }
}