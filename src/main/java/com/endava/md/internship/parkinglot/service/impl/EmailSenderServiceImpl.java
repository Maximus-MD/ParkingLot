package com.endava.md.internship.parkinglot.service.impl;

import com.endava.md.internship.parkinglot.service.EmailSenderService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
public class EmailSenderServiceImpl implements EmailSenderService {

    private final JavaMailSender mailSender;

    public EmailSenderServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendEmail(String recipient, String subject, String body) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        mimeMessage.setFrom(new InternetAddress("parkingLot@mail.com"));
        mimeMessage.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(recipient));
        mimeMessage.setSubject(subject);
        mimeMessage.setText(body);
        mailSender.send(mimeMessage);
    }
}
