package com.endava.md.internship.parkinglot.service;

import jakarta.mail.MessagingException;

public interface EmailService {
    void sendEmail(String recipient, String subject, String body) throws MessagingException;
}