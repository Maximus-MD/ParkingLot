package com.endava.md.internship.parkinglot.validation;

import com.endava.md.internship.parkinglot.repository.UserRepository;
import com.endava.md.internship.parkinglot.validation.validator.UniqueEmailValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class
CustomUniqueEmailValidatorTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UniqueEmailValidator customUniqueEmailValidator;

    @Test
    void isValid_EmailIsNull_ReturnFalse() {
        assertFalse(customUniqueEmailValidator.isValid(null,null));
    }

    @Test
    void isValid_EmailIsEmpty_ReturnFalse() {
        assertFalse(customUniqueEmailValidator.isValid("",null));
    }

    @Test
    void isValid_EmailIsValid_ReturnsTrue() {
        String email = "test@endava.com";
        when(userRepository.existsByEmail(email)).thenReturn(false);

        assertTrue(customUniqueEmailValidator.isValid(email,null));
    }

    @Test
    void isValid_EmailIsInvalid_ReturnsFalse() {
        String email = "test@endava.com";
        when(userRepository.existsByEmail(email)).thenReturn(true);

        assertFalse(customUniqueEmailValidator.isValid(email,null));
    }
}