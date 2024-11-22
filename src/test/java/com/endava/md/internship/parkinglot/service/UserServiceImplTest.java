package com.endava.md.internship.parkinglot.service;

import static com.endava.md.internship.parkinglot.model.RoleEnum.ROLE_ADMIN;
import static com.endava.md.internship.parkinglot.model.RoleEnum.ROLE_REGULAR;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.endava.md.internship.parkinglot.dto.RegistrationRequestDto;
import com.endava.md.internship.parkinglot.dto.RegistrationResponseDto;
import com.endava.md.internship.parkinglot.dto.RoleSwitchResponseDto;
import com.endava.md.internship.parkinglot.exception.CustomAuthException;
import com.endava.md.internship.parkinglot.exception.RegistrationException;
import com.endava.md.internship.parkinglot.exception.RoleNotFoundException;
import com.endava.md.internship.parkinglot.model.Role;
import com.endava.md.internship.parkinglot.model.User;
import com.endava.md.internship.parkinglot.repository.RoleRepository;
import com.endava.md.internship.parkinglot.repository.UserRepository;
import com.endava.md.internship.parkinglot.security.JWTService;
import com.endava.md.internship.parkinglot.service.impl.UserServiceImpl;
import com.endava.md.internship.parkinglot.utils.RoleUtils;
import com.endava.md.internship.parkinglot.utils.UserUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;


class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private EmailSenderService emailSenderService;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JWTService jwtService;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldFailToRegister_WhenEmailAlreadyExists() {
        String existingEmail = "duplicate@example.com";
        RegistrationRequestDto requestDto = new RegistrationRequestDto(
                "TestUser",
                existingEmail,
                "Password1@",
                "123456789");
        when(userRepository.existsByEmailIgnoreCase(existingEmail)).thenReturn(true);

        RegistrationException exception = assertThrows(RegistrationException.class, () ->
                userService.registerNewUser(requestDto));

        assertEquals("Duplicate email", exception.getMessage());
        assertEquals(3001, exception.getErrorCode());
    }

    @Test
    void shouldFailToRegister_WhenPhoneAlreadyExists() {
        String existingPhone = "061111111";
        RegistrationRequestDto requestDto = new RegistrationRequestDto(
                "TestUser",
                "unique@example.com",
                "Password1@",
                existingPhone
        );

        when(userRepository.existsByPhone(existingPhone)).thenReturn(true);

        RegistrationException exception = assertThrows(RegistrationException.class, () ->
                userService.registerNewUser(requestDto));

        assertEquals("Duplicate phone", exception.getMessage());
        assertEquals(3002, exception.getErrorCode());
    }

    @Test
    void shouldRegisterNewUserSuccessfully() {
        RegistrationRequestDto requestDto = new RegistrationRequestDto(
                "TestUser",
                "unique@example.com",
                "Password1@",
                "987654321"
        );
        when(userRepository.existsByEmailIgnoreCase(requestDto.email())).thenReturn(false);
        when(userRepository.existsByPhone(requestDto.phone())).thenReturn(false);

        Role regularRole = new Role();
        regularRole.setRoleName(ROLE_REGULAR);
        when(roleRepository.findByRoleName(ROLE_REGULAR)).thenReturn(Optional.of(regularRole));

        User newUser = new User(null, "TestUser", "unique@example.com", passwordEncoder.encode("Password1@"), "987654321", regularRole);
        when(userRepository.save(any(User.class))).thenReturn(newUser);

        RegistrationResponseDto response = userService.registerNewUser(requestDto);

        assertNotNull(response);
        assertTrue(response.success());
        assertNull(response.token());
        assertNull(response.error());

        verify(userRepository).save(any(User.class));
    }

    @Test
    void setNewRoleTest_WhenRoleChangesReturnsRoleSwitchResponseDto() {
        Role role = RoleUtils.getPreparedAdminRole();
        User user = UserUtils.getPreparedUser();
        user.setRole(role);
        String expectedEmail = UserUtils.getPreparedEmail();

        when(userRepository.findByEmailIgnoreCase(anyString())).thenReturn(Optional.of(user));
        when(roleRepository.findByRoleName(ROLE_REGULAR)).thenReturn(Optional.of(role));

        RoleSwitchResponseDto actualResponseDTO = userService.setNewRole(expectedEmail, ROLE_REGULAR);

        assertEquals(expectedEmail, actualResponseDTO.email());
        assertTrue(actualResponseDTO.result());
        assertEquals(ROLE_ADMIN.name(), actualResponseDTO.newRole());
    }

    @Test
    void setNewRoleTest_WhenRoleHasntChangesReturnsRoleSwitchResponseDto() {
        Role role = RoleUtils.getPreparedAdminRole();
        User user = UserUtils.getPreparedUser();
        String expectedEmail = UserUtils.getPreparedEmail();

        when(userRepository.findByEmailIgnoreCase(anyString())).thenReturn(Optional.of(user));
        when(roleRepository.findByRoleName(ROLE_REGULAR)).thenReturn(Optional.of(role));

        RoleSwitchResponseDto actualResponseDTO = userService.setNewRole(expectedEmail, ROLE_REGULAR);

        assertEquals(expectedEmail, actualResponseDTO.email());
        assertTrue(actualResponseDTO.result());
        assertEquals(ROLE_REGULAR.name(), actualResponseDTO.newRole());
    }

    @Test
    void setNewRoleTest_WhenUserNotFoundThrowsCustomAuthException() {
        when(userRepository.findByEmailIgnoreCase(anyString())).thenReturn(Optional.empty());

        CustomAuthException resultException = assertThrows(CustomAuthException.class, () ->
                userService.setNewRole("test", ROLE_REGULAR));

        assertEquals("User with email test not found", resultException.getMessage());
    }

    @Test
    void setNewRoleTest_WhenRoleNotFoundThrowsEmailSendException() {
        User user = UserUtils.getPreparedUser();

        when(userRepository.findByEmailIgnoreCase(anyString())).thenReturn(Optional.of(user));

        RoleNotFoundException resultException = assertThrows(RoleNotFoundException.class, () ->
                userService.setNewRole("test", ROLE_ADMIN));

        assertEquals(ROLE_ADMIN.name(), resultException.getMessage());
    }
}
