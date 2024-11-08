package com.endava.md.internship.parkinglot.security;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.text.ParseException;
import java.time.Duration;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class JWTUtils {
    private final JWTService jwtService;

    @Value("${JWT_SECRET_KEY_STRING}")
    private static String SECRET_KEY_STRING;
    private static final SecretKey ACCESS_SECRET_KEY = new SecretKeySpec(SECRET_KEY_STRING.getBytes(), "HmacSHA256");

    private static final String ROLE = "role";

    private static final String TOKEN_TYPE = "token_type";
    private static final String TOKEN_TYPE_VALUE = "access";

    private static final Duration EXPIRATION_RATE = Duration.ofDays(1);

    private static final JWSAlgorithm jwsAlgorithm = JWSAlgorithm.HS256;

    protected String generateAccessToken(final String email) {
        try {
            String roleName = jwtService.findUserByEmail(email).getRole().getName().name();
            JWSSigner signer = new MACSigner(ACCESS_SECRET_KEY);
            JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                    .subject(email)
                    .expirationTime(new Date(System.currentTimeMillis() + EXPIRATION_RATE.toMillis()))
                    .claim(TOKEN_TYPE, TOKEN_TYPE_VALUE)
                    .claim(ROLE, roleName)
                    .build();

            SignedJWT signedJWT = new SignedJWT(new JWSHeader(jwsAlgorithm), claimsSet);
            signedJWT.sign(signer);
            return signedJWT.serialize();
        } catch (Exception exception) {
            log.error(exception.getMessage());
            throw new AccessDeniedException("4023");
        }
    }

    protected String extractSubject(final String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);

            return signedJWT.getJWTClaimsSet().getSubject();
        } catch (Exception exception) {
            log.error(exception.getMessage());
            throw new AccessDeniedException("4013");
        }
    }

    protected boolean isValidatedAccessToken(final String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);

            return isTokenExpired(signedJWT) && isTokenValid(signedJWT);
        } catch (ParseException | JOSEException exception) {
            log.error(exception.getMessage());
            throw new AccessDeniedException("4013");
        }
    }

    private boolean isTokenExpired(final SignedJWT signedJWT) throws ParseException {
        Date currentDate = new Date();

        return currentDate.before(signedJWT.getJWTClaimsSet().getExpirationTime());
    }

    private boolean isTokenValid(final SignedJWT signedJWT) throws JOSEException {
        JWSVerifier verifier = new MACVerifier(JWTUtils.ACCESS_SECRET_KEY);

        return signedJWT.verify(verifier);
    }
}
