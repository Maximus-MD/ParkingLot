package com.endava.md.internship.parkinglot.service.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmailSenderServiceTest {

    @Mock
    JavaMailSender mailSender;

    @InjectMocks
    EmailSenderServiceImpl emailSenderService;


    @Test
    void sendPasswordRestorationMessage_sendsSuccessfully() throws MessagingException {
        String recipientEmail = "test@email.com";
        String subject = "Password Restoration";
        String message = "Password Restoration message";
        MimeMessage mimeMessage = mock(MimeMessage.class);
        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);

        emailSenderService.sendEmail(recipientEmail,subject,message);

        verify(mailSender,times(1)).send(mimeMessage);
        verify(mimeMessage,times(1)).setSubject(subject);
        verify(mimeMessage,times(1)).setFrom(new InternetAddress("parkingLot@mail.com"));
        verify(mimeMessage, times(1)).setRecipient(MimeMessage.RecipientType.TO,new InternetAddress(recipientEmail));
        verify(mimeMessage, times(1)).setText(message);

    }
}