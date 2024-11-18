package com.endava.md.internship.parkinglot.security;

import com.endava.md.internship.parkinglot.model.User;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Component
public class JWTRequestFilter extends OncePerRequestFilter {

    private static final String BEARER = "Bearer ";
    private static final String HEADER = "Authorization";

    private final JWTService jwtService;

    private final JWTUtils jwtUtils;

    @Override
    protected void doFilterInternal(@Nonnull HttpServletRequest request,
                                    @Nonnull HttpServletResponse response,
                                    @Nonnull FilterChain filterChain) throws ServletException, IOException {
        String token = authorizationHeaderExtractor(request);

        if (token != null && jwtUtils.isValidatedAccessToken(token) &&
                SecurityContextHolder.getContext().getAuthentication() == null) {

            String email = jwtUtils.extractSubject(token);
            User user = jwtService.findUserByEmail(email);
            setUserAuthentication(request, user, new ArrayList<>());
        }
        filterChain.doFilter(request, response);
    }

    private String authorizationHeaderExtractor(final HttpServletRequest request) {
        String authorizationHeader = request.getHeader(HEADER);
        log.debug("Authorization header: {}", authorizationHeader);

        if (authorizationHeader != null && authorizationHeader.startsWith(BEARER)) {
            return authorizationHeader.substring(BEARER.length());
        }
        return authorizationHeader;
    }

    private void setUserAuthentication(final HttpServletRequest request, final User user,
                                       final List<SimpleGrantedAuthority> permissions) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthToken = new UsernamePasswordAuthenticationToken(
                user.getEmail(),null, permissions);
        usernamePasswordAuthToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthToken);
        logger.debug("User set into SecurityContextHolder, token authentication success");
    }
}