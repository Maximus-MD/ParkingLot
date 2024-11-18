package com.endava.md.internship.parkinglot.service;

import jakarta.mail.MessagingException;

public interface PasswordRestorationService {
    void restorePassword(String email) throws MessagingException;
}
