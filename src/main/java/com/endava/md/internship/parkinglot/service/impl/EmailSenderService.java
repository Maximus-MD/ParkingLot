package com.endava.md.internship.parkinglot.service.impl;

import com.endava.md.internship.parkinglot.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailSenderService implements EmailService {

    private final JavaMailSender mailSender;

    public void sendEmail(String email, String subject, String body) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        mimeMessage.setFrom(new InternetAddress("parkingLot@mail.com"));
        mimeMessage.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(email));
        mimeMessage.setSubject(subject);
        mimeMessage.setText(body);
        mailSender.send(mimeMessage);
    }
}
