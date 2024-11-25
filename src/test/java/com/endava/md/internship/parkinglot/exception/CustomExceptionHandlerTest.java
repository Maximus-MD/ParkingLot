package com.endava.md.internship.parkinglot.exception;

import com.endava.md.internship.parkinglot.dto.LoginResponseDto;
import com.endava.md.internship.parkinglot.dto.RegistrationResponseDto;
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

    @Test
    void handleValidationExceptionsTest() {
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(new Object(), "objectName");
        bindingResult.addError(new FieldError("RequestSomethingDTO", "name", "2001"));

        when(argumentNotValidException.getBindingResult()).thenReturn(bindingResult);
        ResponseEntity<RegistrationResponseDto> response = exceptionHandler.handleValidationExceptions(argumentNotValidException);

        assertNotNull(response.getBody());
        assertFalse(response.getBody().success());
        assertThat(response.getBody().error()).containsExactly(2001);
    }

    @Test
    void handleJwtTokenGenerationErrorTest() throws Exception {
        Field serverErrorField = CustomExceptionHandler.class.getDeclaredField("jwtTokenGenerationError");
        serverErrorField.setAccessible(true);
        serverErrorField.set(exceptionHandler, jwtTokenGenerationErrorCode);

        authException = new CustomAuthException(JWT_TOKEN_GENERATION_ERROR, "bad generation very bad");
        ResponseEntity<LoginResponseDto> response = exceptionHandler.handleCustomAuthException(authException);

        assertNotNull(response.getBody());
        assertFalse(response.getBody().success());
        assertThat(response.getBody().error()).containsExactly(jwtTokenGenerationErrorCode);
    }

    @Test
    void handleBadCredentialsTest() throws Exception {
        Field serverErrorField = CustomExceptionHandler.class.getDeclaredField("badCredentials");
        serverErrorField.setAccessible(true);
        serverErrorField.set(exceptionHandler, badCredentialsCode);

        authException = new CustomAuthException(BAD_CREDENTIALS, "bad very bad");
        ResponseEntity<LoginResponseDto> response = exceptionHandler.handleCustomAuthException(authException);

        assertNotNull(response.getBody());
        assertFalse(response.getBody().success());
        assertThat(response.getBody().error()).containsExactly(badCredentialsCode);
    }

    @Test
    void handleUserNotFoundTest() throws Exception {
        Field serverErrorField = CustomExceptionHandler.class.getDeclaredField("userNotFound");
        serverErrorField.setAccessible(true);
        serverErrorField.set(exceptionHandler, userNotFoundCode);

        authException = new CustomAuthException(USER_NOT_FOUND, "not found very not");
        ResponseEntity<LoginResponseDto> response = exceptionHandler.handleCustomAuthException(authException);

        assertNotNull(response.getBody());
        assertFalse(response.getBody().success());
        assertThat(response.getBody().error()).containsExactly(userNotFoundCode);
    }

    @Test
    void handleInvalidJWTTest() throws Exception {
        Field serverErrorField = CustomExceptionHandler.class.getDeclaredField("invalidJwt");
        serverErrorField.setAccessible(true);
        serverErrorField.set(exceptionHandler, invalidJwtCode);

        authException = new CustomAuthException(INVALID_JWT, "invalid very invalid");
        ResponseEntity<LoginResponseDto> response = exceptionHandler.handleCustomAuthException(authException);

        assertNotNull(response.getBody());
        assertFalse(response.getBody().success());
        assertThat(response.getBody().error()).containsExactly(invalidJwtCode);
    }

    @Test
    void handleGenericErrorTest() throws Exception {
        Field serverErrorField = CustomExceptionHandler.class.getDeclaredField("serverError");
        serverErrorField.setAccessible(true);
        serverErrorField.set(exceptionHandler, serverErrorCode);

        emailSendException = new EmailSendException("valey valey exception");
        ResponseEntity<RegistrationResponseDto> response = exceptionHandler.handleGenericError(emailSendException);

        assertNotNull(response.getBody());
        assertFalse(response.getBody().success());
        assertThat(response.getBody().error()).containsExactly(serverErrorCode);
    }
}