package com.endava.md.internship.parkinglot.validation;

import com.endava.md.internship.parkinglot.repository.UserRepository;
import com.endava.md.internship.parkinglot.validation.validator.UniquePhoneValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
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

        assertThat(customUniquePhoneValidator.isValid(phone, null)).isTrue();
    }

    @Test
    void isValid_PhoneIsInvalid_ReturnsFalse() {
        String phone = "123456789";
        when(userRepository.existsByPhone(phone)).thenReturn(true);

        assertThat(customUniquePhoneValidator.isValid(phone, null)).isFalse();
    }
}