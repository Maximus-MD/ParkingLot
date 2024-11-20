package com.endava.md.internship.parkinglot.service.impl;

import com.endava.md.internship.parkinglot.exception.CustomAuthException;
import com.endava.md.internship.parkinglot.model.User;
import com.endava.md.internship.parkinglot.repository.UserRepository;
import com.endava.md.internship.parkinglot.utils.UserUtils;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
class PasswordRestorationServiceTest {

    @Mock
    EmailSenderServiceImpl emailSenderService;

    @Mock
    UserRepository userRepository;

    @Spy
    PasswordEncoder passwordEncoder;

    @InjectMocks
    PasswordRestorationServiceImpl passwordRestorationService;

    @Test
    void testPasswordRestorationService_userWithThatEmailDoesNOtExist_throwsRuntimeException() {
        String recipientEmail = "Alex@gmail.com";
        when(userRepository.findByEmailIgnoreCase(recipientEmail)).thenReturn(Optional.empty());

        assertThrows(CustomAuthException.class, () -> passwordRestorationService.restorePassword(recipientEmail));
        verify(userRepository, times(1)).findByEmailIgnoreCase(recipientEmail);
    }

    @Test
    void testRestorePassword_emailHasBeenSentSuccessfully_restoresPassword() throws MessagingException {
        String recipientEmail = "Alex@gmail.com";
        String password ="!A3fhgy";
        when(userRepository.findByEmailIgnoreCase(recipientEmail)).thenReturn(Optional.of(UserUtils.getPreparedUser()));
        when(passwordEncoder.encode(anyString())).thenReturn(password);
        User user = UserUtils.getPreparedUser();
        user.setPassword(password);
        doNothing().when(emailSenderService).sendEmail(anyString(), anyString(), anyString());

        passwordRestorationService.restorePassword(recipientEmail);

        verify(userRepository, times(1)).findByEmailIgnoreCase(recipientEmail);
        verify(emailSenderService, times(1)).sendEmail(anyString(), anyString(), anyString());
        verify(passwordEncoder, times(1)).encode(anyString());
    }

    @Test
    void testRestorePassword_errorSendingEmail_throwsRuntimeException() throws MessagingException {
        String recipientEmail = "Alex@gmail.com";

        when(userRepository.findByEmailIgnoreCase(recipientEmail)).thenReturn(Optional.of(UserUtils.getPreparedUser()));
        doThrow(new MessagingException()).when(emailSenderService).sendEmail(anyString(), anyString(), anyString());

        assertThrows(MessagingException.class, () -> passwordRestorationService.restorePassword(recipientEmail));
        verify(userRepository, times(1)).findByEmailIgnoreCase(recipientEmail);
        verify(emailSenderService, times(1)).sendEmail(anyString(), anyString(), anyString());
    }
}