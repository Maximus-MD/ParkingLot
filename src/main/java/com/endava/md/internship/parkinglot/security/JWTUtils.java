package com.endava.md.internship.parkinglot.security;

import com.endava.md.internship.parkinglot.exception.CustomAuthException;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.text.ParseException;
import java.time.Duration;
import java.util.Date;

import static com.endava.md.internship.parkinglot.exception.AuthErrorTypeEnum.INVALID_JWT;
import static com.endava.md.internship.parkinglot.exception.AuthErrorTypeEnum.JWT_TOKEN_GENERATION_ERROR;

@Slf4j
@Component
public class JWTUtils {

    private static final String TOKEN_TYPE = "token_type";
    private static final String TOKEN_TYPE_VALUE = "access";

    private static final Duration EXPIRATION_RATE = Duration.ofDays(1);

    @Value("${JWT_SECRET_KEY_STRING}")
    private String SECRET_KEY_STRING;
    private SecretKey ACCESS_SECRET_KEY;

    private static final JWSAlgorithm jwsAlgorithm = JWSAlgorithm.HS256;

    @PostConstruct
    private void initKeyGenerator() {
        try {
            MessageDigest hashGenerator = MessageDigest.getInstance("SHA-256");
            byte[] keyBytes = hashGenerator.digest(SECRET_KEY_STRING.getBytes());
            ACCESS_SECRET_KEY = new SecretKeySpec(keyBytes, "HmacSHA256");
            log.info("JWT Secret key initialized successfully");
        } catch (Exception exception) {
            throw new CustomAuthException(JWT_TOKEN_GENERATION_ERROR, exception.getMessage());
        }
    }

    protected String generateAccessToken(final String email) {
        try {
            JWSSigner signer = new MACSigner(ACCESS_SECRET_KEY);
            JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                    .subject(email)
                    .expirationTime(new Date(System.currentTimeMillis() + EXPIRATION_RATE.toMillis()))
                    .claim(TOKEN_TYPE, TOKEN_TYPE_VALUE)
                    .build();

            SignedJWT signedJWT = new SignedJWT(new JWSHeader(jwsAlgorithm), claimsSet);
            signedJWT.sign(signer);
            return signedJWT.serialize();
        } catch (Exception exception) {
            throw new CustomAuthException(JWT_TOKEN_GENERATION_ERROR, exception.getMessage());
        }
    }

    protected String extractSubject(final String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);

            return signedJWT.getJWTClaimsSet().getSubject();
        } catch (Exception exception) {
            throw new CustomAuthException(INVALID_JWT, exception.getMessage());
        }
    }

    protected boolean isValidatedAccessToken(final String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);

            return isTokenExpired(signedJWT) && isTokenValid(signedJWT);
        } catch (ParseException | JOSEException exception) {
            throw new CustomAuthException(INVALID_JWT, exception.getMessage());
        }
    }

    private boolean isTokenExpired(final SignedJWT signedJWT) throws ParseException {
        Date currentDate = new Date();

        return currentDate.before(signedJWT.getJWTClaimsSet().getExpirationTime());
    }

    private boolean isTokenValid(final SignedJWT signedJWT) throws JOSEException {
        JWSVerifier verifier = new MACVerifier(ACCESS_SECRET_KEY);

        return signedJWT.verify(verifier);
    }
}
