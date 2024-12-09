package com.endava.md.internship.parkinglot.exception;

import com.endava.md.internship.parkinglot.dto.ResponseDTO;
import com.endava.md.internship.parkinglot.dto.ResponseGenericErrorDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.lang.reflect.Field;

import static com.endava.md.internship.parkinglot.exception.AuthErrorTypeEnum.BAD_CREDENTIALS;
import static com.endava.md.internship.parkinglot.exception.AuthErrorTypeEnum.INVALID_JWT;
import static com.endava.md.internship.parkinglot.exception.AuthErrorTypeEnum.JWT_TOKEN_GENERATION_ERROR;
import static com.endava.md.internship.parkinglot.exception.AuthErrorTypeEnum.USER_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomExceptionHandlerTest {

    @Mock
    private MethodArgumentNotValidException argumentNotValidException;

    @Mock
    private CustomAuthException authException;

    @Mock
    private EmailSendException emailSendException;

    @Mock
    private ParkingLotException parkingLotException;

    @Mock
    private OccupiedParkingSpotException occupiedParkingSpotException;

    @Mock
    private ParkingSpotNotFoundException parkingSpotNotFoundException;

    @Mock
    private ParkingSpotClosedException parkingSpotClosedException;

    @Mock
    private UserAlreadyHasParkingSpotException userAlreadyHasParkingSpotException;

    @InjectMocks
    CustomExceptionHandler exceptionHandler;

    @Value("${message.jwt-generation-error}")
    private int jwtTokenGenerationErrorCode;

    @Value("${message.bad.credentials}")
    private int badCredentialsCode;

    @Value("${message.user-not-found}")
    private int userNotFoundCode;

    @Value("${message.invalid-jwt}")
    private int invalidJwtCode;

    @Value("${message.server-error}")
    private int serverErrorCode;

    @Value("${message.invalid-name}")
    private int invalidNameCode;

    @Value("${message.parking-not-found}")
    private int parkingNotFoundCode;

    @Value("${message.occupied-parking-spot}")
    private int occupiedParkingSpotErrorCode;

    @Value("${message.parking-spot-not-found}")
    private int parkingSpotNotFoundErrorCode;

    @Value("${message.user-has-parking-spot}")
    private int userHasParkingSpotErrorCode;

    @Value("${message.inaccessible-parking-spot}")
    private int parkingSpotClosedErrorCode;

    @Test
    void handleValidationExceptionsTest() {
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(new Object(), "objectName");
        bindingResult.addError(new FieldError("RequestSomethingDTO",
                "name", String.valueOf(invalidNameCode)));

        when(argumentNotValidException.getBindingResult()).thenReturn(bindingResult);
        ResponseEntity<ResponseDTO> response = exceptionHandler.handleValidationExceptions(argumentNotValidException);

        assertNotNull(response.getBody());
        assertFalse(response.getBody().isSuccess());
        assertThat(response.getBody().getError()).containsExactly(invalidNameCode);
    }

    @Test
    void handleParkingNotFoundTest() throws Exception {
        Field serverErrorField = CustomExceptionHandler.class.getDeclaredField("parkingLotNotFound");
        serverErrorField.setAccessible(true);
        serverErrorField.set(exceptionHandler, parkingNotFoundCode);

        parkingLotException = new ParkingLotException(parkingNotFoundCode + "not found");
        ResponseEntity<ResponseGenericErrorDTO> response = exceptionHandler.handleParkingLotException(parkingLotException);
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isSuccess());
        assertThat(response.getBody().getError()).containsExactly(parkingNotFoundCode);
    }

    @Test
    void handleJwtTokenGenerationErrorTest() throws Exception {
        Field serverErrorField = CustomExceptionHandler.class.getDeclaredField("jwtTokenGenerationError");
        serverErrorField.setAccessible(true);
        serverErrorField.set(exceptionHandler, jwtTokenGenerationErrorCode);

        authException = new CustomAuthException(JWT_TOKEN_GENERATION_ERROR, "bad generation very bad");
        ResponseEntity<ResponseDTO> response = exceptionHandler.handleCustomAuthException(authException);

        assertNotNull(response.getBody());
        assertFalse(response.getBody().isSuccess());
        assertThat(response.getBody().getError()).containsExactly(jwtTokenGenerationErrorCode);
    }

    @Test
    void handleBadCredentialsTest() throws Exception {
        Field serverErrorField = CustomExceptionHandler.class.getDeclaredField("badCredentials");
        serverErrorField.setAccessible(true);
        serverErrorField.set(exceptionHandler, badCredentialsCode);

        authException = new CustomAuthException(BAD_CREDENTIALS, "bad very bad");
        ResponseEntity<ResponseDTO> response = exceptionHandler.handleCustomAuthException(authException);

        assertNotNull(response.getBody());
        assertFalse(response.getBody().isSuccess());
        assertThat(response.getBody().getError()).containsExactly(badCredentialsCode);
    }

    @Test
    void handleUserNotFoundTest() throws Exception {
        Field serverErrorField = CustomExceptionHandler.class.getDeclaredField("userNotFound");
        serverErrorField.setAccessible(true);
        serverErrorField.set(exceptionHandler, userNotFoundCode);

        authException = new CustomAuthException(USER_NOT_FOUND, "not found very not");
        ResponseEntity<ResponseDTO> response = exceptionHandler.handleCustomAuthException(authException);

        assertNotNull(response.getBody());
        assertFalse(response.getBody().isSuccess());
        assertThat(response.getBody().getError()).containsExactly(userNotFoundCode);
    }

    @Test
    void handleInvalidJWTTest() throws Exception {
        Field serverErrorField = CustomExceptionHandler.class.getDeclaredField("invalidJwt");
        serverErrorField.setAccessible(true);
        serverErrorField.set(exceptionHandler, invalidJwtCode);

        authException = new CustomAuthException(INVALID_JWT, "invalid very invalid");
        ResponseEntity<ResponseDTO> response = exceptionHandler.handleCustomAuthException(authException);

        assertNotNull(response.getBody());
        assertFalse(response.getBody().isSuccess());
        assertThat(response.getBody().getError()).containsExactly(invalidJwtCode);
    }

    @Test
    void handleGenericErrorTest() throws Exception {
        Field serverErrorField = CustomExceptionHandler.class.getDeclaredField("serverError");
        serverErrorField.setAccessible(true);
        serverErrorField.set(exceptionHandler, serverErrorCode);

        emailSendException = new EmailSendException("valey valey exception");
        ResponseEntity<ResponseGenericErrorDTO> response = exceptionHandler.handleGenericError(emailSendException);

        assertNotNull(response.getBody());
        assertFalse(response.getBody().isSuccess());
        assertThat(response.getBody().getError()).containsExactly(serverErrorCode);
    }

    @Test
    void handleOccupiedParkingSpotExceptionTest() throws NoSuchFieldException, IllegalAccessException {
        Field serverErrorField = CustomExceptionHandler.class.getDeclaredField("occupiedParkingSpot");
        serverErrorField.setAccessible(true);
        serverErrorField.set(exceptionHandler, occupiedParkingSpotErrorCode);

        occupiedParkingSpotException = new OccupiedParkingSpotException("Parking Spot is Occupied!");
        ResponseEntity<ResponseGenericErrorDTO> response =
                exceptionHandler.handleOccupiedParkingSpotException(occupiedParkingSpotException);
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isSuccess());
        assertThat(response.getBody().getError()).containsExactly(occupiedParkingSpotErrorCode);
    }

    @Test
    void handleParkingSpotNotFoundExceptionTest() throws NoSuchFieldException, IllegalAccessException {
        Field serverErrorField = CustomExceptionHandler.class.getDeclaredField("parkingSpotNotFound");
        serverErrorField.setAccessible(true);
        serverErrorField.set(exceptionHandler, parkingSpotNotFoundErrorCode);

        parkingSpotNotFoundException = new ParkingSpotNotFoundException("Parking Spot is Not Found!");
        ResponseEntity<ResponseGenericErrorDTO> response =
                exceptionHandler.handleParkingSpotNotFoundException(parkingSpotNotFoundException);
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isSuccess());
        assertThat(response.getBody().getError()).containsExactly(parkingSpotNotFoundErrorCode);
    }

    @Test
    void handleParkingSpotClosedExceptionTest() throws NoSuchFieldException, IllegalAccessException {
        Field serverErrorField = CustomExceptionHandler.class.getDeclaredField("inaccessibleParkingSpot");
        serverErrorField.setAccessible(true);
        serverErrorField.set(exceptionHandler, parkingSpotClosedErrorCode);

        parkingSpotClosedException = new ParkingSpotClosedException("Parking spot is temporary closed!");
        ResponseEntity<ResponseGenericErrorDTO> response =
                exceptionHandler.handleParkingSpotClosedException(parkingSpotClosedException);
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isSuccess());
        assertThat(response.getBody().getError()).containsExactly(parkingSpotClosedErrorCode);
    }

    @Test
    void handleUserAlreadyHasParkingSpotExceptionExceptionTest() throws NoSuchFieldException, IllegalAccessException {
        Field serverErrorField = CustomExceptionHandler.class.getDeclaredField("userHasParkingSpot");
        serverErrorField.setAccessible(true);
        serverErrorField.set(exceptionHandler, userHasParkingSpotErrorCode);

        userAlreadyHasParkingSpotException = new UserAlreadyHasParkingSpotException("User has already parking spot!");
        ResponseEntity<ResponseGenericErrorDTO> response =
                exceptionHandler.handleUserAlreadyHasParkingSpotExceptionException(userAlreadyHasParkingSpotException);
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isSuccess());
        assertThat(response.getBody().getError()).containsExactly(userHasParkingSpotErrorCode);
    }
}