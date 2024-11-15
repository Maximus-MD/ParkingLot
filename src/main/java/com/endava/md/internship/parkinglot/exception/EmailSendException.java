package com.endava.md.internship.parkinglot.exception;

import org.springframework.mail.MailSendException;

public class EmailSendException extends MailSendException {
    public EmailSendException(String message) {
        super(message);
    }
}
