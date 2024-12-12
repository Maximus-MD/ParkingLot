package com.endava.md.internship.parkinglot.security;

import com.endava.md.internship.parkinglot.dto.LoginRequestDto;
import com.endava.md.internship.parkinglot.model.User;
import com.endava.md.internship.parkinglot.utils.ResponseDTOUtils;
import com.endava.md.internship.parkinglot.utils.swagger.TokenUtils;
import com.endava.md.internship.parkinglot.utils.UserUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JWTRequestFilterTest {
    @Mock
    private JWTService jwtService;

    @Mock
    private JWTUtils jwtUtils;

    @InjectMocks
    private JWTRequestFilter jwtRequestFilter;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void doFilterInternalTest_WhenRequestHaveCorrectTokenReturnsSecurityContextHolderWithAuthenticationTrue() throws Exception {
        SecurityContextHolder.clearContext();
        LoginRequestDto loginRequestDTO = ResponseDTOUtils.getPreparedRequestDto();
        String token = TokenUtils.getPreparedToken();
        User user = UserUtils.getPreparedUser();

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setMethod("POST");
        request.setRequestURI("/something");
        request.setContentType("application/json");
        request.setCharacterEncoding("UTF-8");
        request.addHeader("Authorization", "Bearer " + token);
        request.setContent(objectMapper.writeValueAsBytes(loginRequestDTO));

        MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain mockChain = mock(FilterChain.class);

        when(jwtUtils.isValidatedAccessToken(token)).thenReturn(Boolean.TRUE);
        when(jwtUtils.extractSubject(token)).thenReturn(loginRequestDTO.email());
        when(jwtService.findUserByEmail(loginRequestDTO.email())).thenReturn(user);

        Method doFilterInternalMethod = JWTRequestFilter.class.getDeclaredMethod("doFilterInternal",
                HttpServletRequest.class, HttpServletResponse.class, FilterChain.class);
        doFilterInternalMethod.setAccessible(true);

        doFilterInternalMethod.invoke(jwtRequestFilter, request, response, mockChain);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        assertNotNull(authentication);
        assertTrue(authentication.isAuthenticated());
    }

    @Test
    void doFilterInternalTest_WhenRequestDontHaveTokenReturnsNullAuthenticationIntoSecurityContextHolder() throws Exception {
        SecurityContextHolder.clearContext();
        LoginRequestDto loginRequestDTO = ResponseDTOUtils.getPreparedRequestDto();

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setMethod("POST");
        request.setRequestURI("/something");
        request.setContentType("application/json");
        request.setCharacterEncoding("UTF-8");
        request.setContent(objectMapper.writeValueAsBytes(loginRequestDTO));

        MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain mockChain = mock(FilterChain.class);

        Method doFilterInternalMethod = JWTRequestFilter.class.getDeclaredMethod("doFilterInternal",
                HttpServletRequest.class, HttpServletResponse.class, FilterChain.class);
        doFilterInternalMethod.setAccessible(true);

        doFilterInternalMethod.invoke(jwtRequestFilter, request, response, mockChain);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        assertNull(authentication);
    }

    @Test
    void doFilterInternalTest_WhenRequestHaveInCorrectTokenReturnsSecurityContextHolderWithAuthenticationFalse() throws Exception {
        SecurityContextHolder.clearContext();
        LoginRequestDto loginRequestDTO = ResponseDTOUtils.getPreparedRequestDto();
        String token = TokenUtils.getPreparedToken();

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setMethod("POST");
        request.setRequestURI("/something");
        request.setContentType("application/json");
        request.setCharacterEncoding("UTF-8");
        request.addHeader("Authorization", "Bearer " + token);
        request.setContent(objectMapper.writeValueAsBytes(loginRequestDTO));

        MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain mockChain = mock(FilterChain.class);

        when(jwtUtils.isValidatedAccessToken(token)).thenReturn(Boolean.FALSE);

        Method doFilterInternalMethod = JWTRequestFilter.class.getDeclaredMethod("doFilterInternal",
                HttpServletRequest.class, HttpServletResponse.class, FilterChain.class);
        doFilterInternalMethod.setAccessible(true);

        doFilterInternalMethod.invoke(jwtRequestFilter, request, response, mockChain);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        assertNull(authentication);
    }
}