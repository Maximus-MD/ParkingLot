package com.endava.md.internship.parkinglot.validation;

import com.endava.md.internship.parkinglot.repository.UserRepository;
import com.endava.md.internship.parkinglot.validation.validator.UniquePhoneValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomUniquePhoneValidatorTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UniquePhoneValidator customUniquePhoneValidator;

    @Test
    void isValid_PhoneIsValid_ReturnsTrue() {
        String phone = "123456789";
        when(userRepository.existsByPhone(phone)).thenReturn(false);

        assertTrue(customUniquePhoneValidator.isValid(phone, null));
    }

    @Test
    void isValid_PhoneIsInvalid_ReturnsFalse() {
        String phone = "123456789";
        when(userRepository.existsByPhone(phone)).thenReturn(true);

        assertFalse(customUniquePhoneValidator.isValid(phone, null));
    }
}