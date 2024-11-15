package com.endava.md.internship.parkinglot.service;

import com.endava.md.internship.parkinglot.model.Role;
import com.endava.md.internship.parkinglot.model.RoleEnum;
import com.endava.md.internship.parkinglot.model.User;
import com.endava.md.internship.parkinglot.repository.UserRepository;
import com.endava.md.internship.parkinglot.service.impl.EmailSenderService;
import com.endava.md.internship.parkinglot.service.impl.UserServiceImpl;
import com.endava.md.internship.parkinglot.utils.UserUtils;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EmailServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private EmailSenderService emailService;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void whenTryToChangeRole_ThenSendEmail() throws MessagingException {
        User user = UserUtils.getPreparedUser();
        Role role = new Role();
        role.setRoleName(RoleEnum.ROLE_ADMIN);
        user.setRole(role);

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

        userService.setNewRole(user.getEmail());

        assertEquals(RoleEnum.ROLE_REGULAR, user.getRole().getRoleName());

        verify(emailService).sendEmail(eq("maximilian.stati@endava.com"), eq("Role changed notification."), eq("ROLE_REGULAR"));

        verify(userRepository).save(user);
    }
}