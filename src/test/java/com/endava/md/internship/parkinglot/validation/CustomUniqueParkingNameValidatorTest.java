package com.endava.md.internship.parkinglot.validation;

import com.endava.md.internship.parkinglot.repository.ParkingLotRepository;
import com.endava.md.internship.parkinglot.validation.validator.UniqueParkingNameValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomUniqueParkingNameValidatorTest {

    @Mock
    private ParkingLotRepository parkingLotRepository;

    @InjectMocks
    UniqueParkingNameValidator uniqueParkingNameValidator;

    @Test
    void isValid_ParkingNameIsUnique_ReturnsTrue() {
        String parkingName = "uniqueParkingName";
        when(parkingLotRepository.existsByName(parkingName)).thenReturn(false);

        assertThat(uniqueParkingNameValidator.isValid(parkingName, null)).isTrue();
    }

    @Test
    void isValid_ParkingNameIsNotUnique_ReturnsFalse() {
        String parkingName = "notUniquesParkingName";
        when(parkingLotRepository.existsByName(parkingName)).thenReturn(true);

        assertThat(uniqueParkingNameValidator.isValid(parkingName, null)).isFalse();
    }
}