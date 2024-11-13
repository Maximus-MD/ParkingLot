package com.endava.md.internship.parkinglot.service.impl;

import com.endava.md.internship.parkinglot.service.EmailService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import static com.endava.md.internship.parkinglot.model.RoleEnum.ROLE_ADMIN;
import static com.endava.md.internship.parkinglot.model.RoleEnum.ROLE_REGULAR;

@Service
public class EmailServiceImpl implements EmailService {

    private JavaMailSender mailSender;

    @Override
    public void sendEmail(String to, String newRole) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Role change notification.");

        if(newRole.equals(ROLE_REGULAR.name())){
            message.setText("You have been deprived of Admin role for Parking Lot app.");
        } else if (newRole.equals(ROLE_ADMIN.name())) {
            message.setText("You have been granted an Admin role for Parking Lot app.");
        }

        mailSender.send(message);
    }
}
