package com.endava.md.internship.parkinglot.config;

import com.endava.md.internship.parkinglot.exception.CustomAuthenticationEntryPoint;
import com.endava.md.internship.parkinglot.security.JWTRequestFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class GlobalSecurityFilterConfig {

    private final JWTRequestFilter jwtRequestFilter;

    private final CustomAuthenticationEntryPoint authenticationEntryPoint;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(
                                HttpMethod.POST, "/register", "/login").permitAll()
                        .requestMatchers(HttpMethod.PATCH, "/restore-password").permitAll()
                        .requestMatchers(HttpMethod.GET, "/actuator/health", "/favicon.ico").permitAll()
                        .requestMatchers("/parking-lots/create").hasAuthority("ROLE_ADMIN")
                        .requestMatchers("/parking-lots/delete/").hasAuthority("ROLE_ADMIN")
                        .anyRequest().authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(handler -> handler
                        .authenticationEntryPoint(authenticationEntryPoint))
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
