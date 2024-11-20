package com.endava.md.internship.parkinglot.security;

import com.endava.md.internship.parkinglot.exception.CustomAuthException;
import com.endava.md.internship.parkinglot.model.User;
import com.endava.md.internship.parkinglot.repository.UserRepository;
import com.endava.md.internship.parkinglot.utils.RoleUtils;
import com.endava.md.internship.parkinglot.utils.TokenUtils;
import com.endava.md.internship.parkinglot.utils.UserUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.endava.md.internship.parkinglot.exception.AuthErrorTypeEnum.USER_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JWTServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JWTUtils jwtUtils;

    @InjectMocks
    private JWTService jwtService;

    @Test
    void generateTokenTest_WhenSuccess_ReturnsToken() {
        String token = TokenUtils.getPreparedToken();
        String email = UserUtils.getPreparedEmail();
        String role = RoleUtils.getPreparedRoleName();
        User user = UserUtils.getPreparedUser();

        when(userRepository.findByEmailIgnoreCase(email)).thenReturn(Optional.of(user));
        when(jwtUtils.generateAccessToken(email, role)).thenReturn(token);

        String resultToken = jwtService.generateToken(email);

        assertEquals(resultToken, token);
    }

    @Test
    void generateTokenTest_WhenUserRepositoryReturnsFalse_ThrowsCustomAuthException() {
        User user = UserUtils.getPreparedUser();
        String email = UserUtils.getPreparedEmail();

        when(userRepository.findByEmailIgnoreCase(email)).thenReturn(Optional.of(user));

        CustomAuthException customAuthException = assertThrows(CustomAuthException.class, () ->
                jwtService.generateToken(email));

        assertEquals(USER_NOT_FOUND, customAuthException.getAuthErrorType());
        assertEquals(customAuthException.getMessage(), String.format("User email: %s not found", email));
    }

    @Test
    void findUserByEmailTest_ReturnsUser() {
        User user = UserUtils.getPreparedUser();
        String email = UserUtils.getPreparedEmail();

        when(userRepository.findByEmailIgnoreCase(email)).thenReturn(Optional.of(user));

        User resultUser = jwtService.findUserByEmail(email);

        assertEquals(user.getEmail(), resultUser.getEmail());
        assertEquals(user.getPassword(), resultUser.getPassword());
    }

    @Test
    void findUserByEmailTest_WhenUserNotExist_ThrowsCustomAuthException() {
        String email = UserUtils.getPreparedEmail();

        CustomAuthException customAuthException = assertThrows(CustomAuthException.class, () ->
                jwtService.findUserByEmail(email));

        assertEquals(USER_NOT_FOUND, customAuthException.getAuthErrorType());
        assertEquals(customAuthException.getMessage(), String.format("User email: %s not found", email));
    }
}
