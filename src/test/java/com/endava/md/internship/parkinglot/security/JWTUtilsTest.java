package com.endava.md.internship.parkinglot.security;

import com.endava.md.internship.parkinglot.exception.CustomAuthException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static com.endava.md.internship.parkinglot.exception.AuthErrorTypeEnum.INVALID_JWT;
import static com.endava.md.internship.parkinglot.exception.AuthErrorTypeEnum.JWT_TOKEN_GENERATION_ERROR;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JWTUtilsTest {

    private JWTUtils jwtUtils;

    private static final String SECRET_KEY = "secret-key-for-tests";
    private static final String EMAIL = "Alex@gmail.com";
    private static final String ROLE = "ROLE_REGULAR";
    private static final String INVALID_KEY = "invalid-key";

    @BeforeEach
    void setUp() {
        jwtUtils = new JWTUtils();
        ReflectionTestUtils.setField(jwtUtils, "secretKeyString", SECRET_KEY);
        ReflectionTestUtils.invokeMethod(jwtUtils, "initKeyGenerator");
    }

    @Test
    void generateAccessTokenTest_ReturnsValidToken() {
        String token = jwtUtils.generateAccessToken(EMAIL, ROLE);

        assertNotNull(token);
        assertFalse(token.isEmpty());
    }

    @Test
    void generateAccessTokenTest_ThrowsCustomAuthExceptionWithJWTTokenGenerationErrorType() {
        jwtUtils = new JWTUtils();
        ReflectionTestUtils.setField(jwtUtils, "secretKeyString", null);

        CustomAuthException exception = assertThrows(CustomAuthException.class, () ->
                jwtUtils.generateAccessToken(EMAIL, ROLE));

        assertEquals(JWT_TOKEN_GENERATION_ERROR, exception.getAuthErrorType());
    }

    @Test
    void extractSubjectTest_ReturnsSubject() {
        String token = jwtUtils.generateAccessToken(EMAIL, ROLE);

        assertEquals(EMAIL, jwtUtils.extractSubject(token));
    }

    @Test
    void extractSubjectTest_ThrowsCustomAuthExceptionWithInvalidJWTErrorType() {
        CustomAuthException exception = assertThrows(CustomAuthException.class,
                () -> jwtUtils.extractSubject(INVALID_KEY));

        assertEquals(INVALID_JWT, exception.getAuthErrorType());
    }

    @Test
    void isValidatedAccessToken_ReturnsTrueForValidToken() {
        String token = jwtUtils.generateAccessToken(EMAIL, ROLE);

        assertTrue(jwtUtils.isValidatedAccessToken(token));
    }

    @Test
    void isValidatedAccessToken_ThrowsCustomAuthExceptionWithInvalidJWTErrorType() {
        CustomAuthException exception = assertThrows(CustomAuthException.class, () ->
                jwtUtils.isValidatedAccessToken(INVALID_KEY));

        assertEquals(INVALID_JWT, exception.getAuthErrorType());
    }

    @Test
    void initKeyGenerator_ThrowsCustomAuthExceptionWithJWTTokenGenerationErrorType() {
        jwtUtils = new JWTUtils();
        ReflectionTestUtils.setField(jwtUtils, "secretKeyString", null);

        CustomAuthException exception = assertThrows(CustomAuthException.class, () ->
                ReflectionTestUtils.invokeMethod(jwtUtils, "initKeyGenerator"));

        assertEquals(JWT_TOKEN_GENERATION_ERROR, exception.getAuthErrorType());
    }
}