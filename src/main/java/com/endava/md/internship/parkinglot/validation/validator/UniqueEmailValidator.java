package com.endava.md.internship.parkinglot.validation.validator;

import com.endava.md.internship.parkinglot.repository.UserRepository;
import com.endava.md.internship.parkinglot.validation.annotation.UniqueEmail;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    private final UserRepository userRepository;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        return !userRepository.existsByEmailIgnoreCase(email);
    }
}
