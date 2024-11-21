package com.endava.md.internship.parkinglot.service;

import com.endava.md.internship.parkinglot.dto.LoginRequestDto;
import com.endava.md.internship.parkinglot.dto.LoginResponseDto;
import com.endava.md.internship.parkinglot.exception.CustomAuthException;
import com.endava.md.internship.parkinglot.model.User;
import com.endava.md.internship.parkinglot.repository.UserRepository;
import com.endava.md.internship.parkinglot.security.JWTService;
import com.endava.md.internship.parkinglot.service.impl.AuthServiceImpl;
import com.endava.md.internship.parkinglot.utils.LoginDTOUtils;
import com.endava.md.internship.parkinglot.utils.UserUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static com.endava.md.internship.parkinglot.exception.AuthErrorTypeEnum.BAD_CREDENTIALS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JWTService jwtService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthServiceImpl authService;

    @Test
    void loginTest_ReturnsLoginResponseDto() {
        User user = UserUtils.getPreparedUser();
        LoginRequestDto loginRequestDto = LoginDTOUtils.getPreparedRequestDto();
        LoginResponseDto loginResponseDto = LoginDTOUtils.getPreparedResponseDto();

        when(userRepository.findByEmailIgnoreCase(anyString())).thenReturn(Optional.of(user));

        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);

        when(jwtService.generateToken(anyString())).thenReturn(loginResponseDto.token());

        LoginResponseDto resultLoginResponseDTO = authService.login(loginRequestDto);

        assertEquals(resultLoginResponseDTO.token(), loginResponseDto.token());
    }

    @Test
    void loginTest_WhenUserNotExist_ThrowsCustomAuthException() {
        LoginRequestDto loginRequestDto = LoginDTOUtils.getPreparedRequestDto();

        when(userRepository.findByEmailIgnoreCase(anyString())).thenReturn(Optional.empty());

        CustomAuthException customAuthException = assertThrows(CustomAuthException.class, () ->
                authService.login(loginRequestDto));

        assertEquals(BAD_CREDENTIALS, customAuthException.getAuthErrorType());
    }

    @Test
    void loginTest_WhenUserExistsButPasswordsAreNotMatching_ThrowsCustomAuthException() {
        User user = UserUtils.getPreparedUser();
        LoginRequestDto loginRequestDto = LoginDTOUtils.getPreparedRequestDto();

        when(userRepository.findByEmailIgnoreCase(anyString())).thenReturn(Optional.of(user));

        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

        CustomAuthException customAuthException =
                assertThrows(CustomAuthException.class, () -> authService.login(loginRequestDto));

        assertEquals(BAD_CREDENTIALS, customAuthException.getAuthErrorType());
    }
}
