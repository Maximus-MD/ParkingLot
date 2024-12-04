package com.endava.md.internship.parkinglot.exception;

import com.endava.md.internship.parkinglot.dto.ResponseDTO;
import com.endava.md.internship.parkinglot.dto.ResponseGenericErrorDTO;
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

    @Value("${message.parking-not-found}")
    private int parkingLotNotFound;

    @Value("${message.occupied-parking-spot}")
    private int occupiedParkingSpot;

    @Value("${message.parking-spot-not-found}")
    private int parkingSpotNotFound;

    @Value("1030")
    private int userNotAssigned;

    @Value("1031")
    private int userAlreadyAssigned;

    @Value("3003")
    private int emailSendingError;

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
            case JWT_TOKEN_GENERATION_ERROR -> ResponseFactory.createResponse(Set.of(jwtTokenGenerationError));

            case BAD_CREDENTIALS -> ResponseFactory.createResponse(Set.of(badCredentials));

            case USER_NOT_FOUND -> ResponseFactory.createResponse(Set.of(userNotFound));

            case INVALID_JWT -> ResponseFactory.createResponse(Set.of(invalidJwt));
        };
    }

    @ExceptionHandler(ParkingLotException.class)
    public ResponseEntity<ResponseGenericErrorDTO> handleParkingLotException(final ParkingLotException exception) {
        log.error(exception.getMessage());

        return ResponseFactory.createResponse(parkingLotNotFound);
    }

    @ExceptionHandler(RegistrationException.class)
    public ResponseEntity<ResponseDTO> handleRegistrationException(RegistrationException exception) {
        log.error("Registration error: {}", exception.getMessage(), exception);

        return ResponseFactory.createResponse(Set.of(exception.getErrorCode()));
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ResponseGenericErrorDTO> handleGenericError(Throwable throwable) {
        StackTraceElement traceElement = throwable.getStackTrace()[0];
        log.error("Generic error in class: {}, method: {}, line: {}, the error: {}",
                traceElement.getClassName(), traceElement.getMethodName(),
                traceElement.getLineNumber(), throwable.getMessage(), throwable);

        return ResponseFactory.createResponse(serverError);
    }

    @ExceptionHandler(OccupiedParkingSpotException.class)
    public ResponseEntity<ResponseDTO> handleOccupiedParkingSpotException(
            OccupiedParkingSpotException occupiedParkingSpotException
    ) {
        log.error(occupiedParkingSpotException.getMessage());

        return ResponseFactory.createResponse(Set.of(occupiedParkingSpot));
    }

    @ExceptionHandler(ParkingSpotNotFoundException.class)
    public ResponseEntity<ResponseDTO> handleParkingSpotNotFoundException(
            ParkingSpotNotFoundException parkingSpotNotFoundException
    ) {
        log.error(parkingSpotNotFoundException.getMessage());

        return ResponseFactory.createResponse(Set.of(parkingSpotNotFound));
    }

    @ExceptionHandler(UserAlreadyAssignedException.class)
    public ResponseEntity<ResponseGenericErrorDTO> handleUserAlreadyAssignedException(UserAlreadyAssignedException exception) {
        log.error("User already assigned: {}", exception.getMessage());
        return ResponseFactory.createResponse(userAlreadyAssigned);
    }

    @ExceptionHandler(UserNotAssignedException.class)
    public ResponseEntity<ResponseGenericErrorDTO> handleUserNotAssignedException(UserNotAssignedException exception) {
        log.error("User not assigned: {}", exception.getMessage());
        return ResponseFactory.createResponse(userNotAssigned);
    }

    @ExceptionHandler(EmailSendException.class)
    public ResponseEntity<ResponseGenericErrorDTO> handleEmailSendingException(EmailSendException exception) {
        log.error("Email sending error: {}", exception.getMessage());
        return ResponseFactory.createResponse(emailSendingError);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ResponseGenericErrorDTO> handleUserNotFoundException(UserNotFoundException exception) {
        log.error("User not found: {}", exception.getMessage());
        return ResponseFactory.createResponse(userNotFound);
    }
}