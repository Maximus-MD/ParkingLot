package com.endava.md.internship.parkinglot.service.impl;

import com.endava.md.internship.parkinglot.exception.CustomAuthException;
import com.endava.md.internship.parkinglot.model.User;
import com.endava.md.internship.parkinglot.repository.UserRepository;
import com.endava.md.internship.parkinglot.service.EmailSenderService;
import com.endava.md.internship.parkinglot.service.PasswordRestorationService;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;

import static com.endava.md.internship.parkinglot.exception.AuthErrorTypeEnum.USER_NOT_FOUND;


@Slf4j
@Service
public class PasswordRestorationServiceImpl implements PasswordRestorationService {

    private final UserRepository userRepository;
    private final EmailSenderService emailSenderService;
    private final PasswordEncoder passwordEncoder;

    private static final SecureRandom RANDOM = new SecureRandom();
    private static final String DIGITS = "0123456789";
    private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String SPECIAL_CHARACTERS = "!@#$%^&*()-_=+<>?";
    private static final String ALL_CHARACTERS = DIGITS + UPPERCASE + SPECIAL_CHARACTERS;

    public PasswordRestorationServiceImpl(UserRepository userRepository, EmailSenderService emailSenderService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.emailSenderService = emailSenderService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(rollbackFor = MessagingException.class)
    public void restorePassword(String email) throws MessagingException {
        User user = userRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() ->
                        new CustomAuthException(USER_NOT_FOUND, String.format("User email: %s not found", email)));
        String password = generatePassword();
        String subject = "Password restoration";
        String message = "Your password has been reset. Here is your new password: " + password;
        emailSenderService.sendEmail(email, subject, message);
        user.setPassword(passwordEncoder.encode(password));
    }

    private String generatePassword() {
        int minPasswordLength = 5;
        int maxPasswordLength = 10;
        int bound = maxPasswordLength - minPasswordLength + 1;
        int passwordLength = minPasswordLength + RANDOM.nextInt(bound);
        StringBuilder password = new StringBuilder();

        password.append(randomChar(DIGITS)).append(randomChar(UPPERCASE)).append(randomChar(SPECIAL_CHARACTERS));

        for (int i = 3; i < passwordLength; i++) {
            password.append(randomChar(ALL_CHARACTERS));
        }

        return shufflePassword(password.toString());
    }

    private String shufflePassword(String password) {
        char[] characters = password.toCharArray();
        for (int i = 0; i < characters.length; i++) {
            int randomIndex = RANDOM.nextInt(characters.length);
            char temp = characters[i];
            characters[i] = characters[randomIndex];
            characters[randomIndex] = temp;
        }
        return new String(characters);

    }

    private static char randomChar(String characters) {
        int index = RANDOM.nextInt(characters.length());
        return characters.charAt(index);
    }
}