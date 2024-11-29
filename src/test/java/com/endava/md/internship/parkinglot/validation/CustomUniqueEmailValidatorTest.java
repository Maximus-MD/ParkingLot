package com.endava.md.internship.parkinglot.validation;

import com.endava.md.internship.parkinglot.repository.UserRepository;
import com.endava.md.internship.parkinglot.validation.validator.UniqueEmailValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomUniqueEmailValidatorTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UniqueEmailValidator customUniqueEmailValidator;

    @Test
    void isValid_EmailIsValid_ReturnsTrue() {
        String email = "test@endava.com";
        when(userRepository.existsByEmailIgnoreCase(email)).thenReturn(false);

        assertThat(customUniqueEmailValidator.isValid(email, null)).isTrue();
    }

    @Test
    void isValid_EmailIsInvalid_ReturnsFalse() {
        String email = "test@endava.com";
        when(userRepository.existsByEmailIgnoreCase(email)).thenReturn(true);

        assertThat(customUniqueEmailValidator.isValid(email, null)).isFalse();
    }
}