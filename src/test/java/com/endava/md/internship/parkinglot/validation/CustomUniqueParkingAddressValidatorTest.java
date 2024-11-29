package com.endava.md.internship.parkinglot.validation;

import com.endava.md.internship.parkinglot.repository.ParkingLotRepository;
import com.endava.md.internship.parkinglot.validation.validator.UniqueParkingAddressValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomUniqueParkingAddressValidatorTest {

    @Mock
    private ParkingLotRepository parkingLotRepository;

    @InjectMocks
    UniqueParkingAddressValidator uniqueParkingAddressValidator;

    @Test
    void isValid_ParkingAddressIsUnique_ReturnsTrue() {
        String parkingAddress = "uniqueParkingAddress";
        when(parkingLotRepository.existsByAddress(parkingAddress)).thenReturn(false);

        assertThat(uniqueParkingAddressValidator.isValid(parkingAddress, null)).isTrue();
    }

    @Test
    void isValid_ParkingAddressIsNotUnique_ReturnsFalse() {
        String parkingAddress = "notUniquesParkingAddress";
        when(parkingLotRepository.existsByAddress(parkingAddress)).thenReturn(true);

        assertThat(uniqueParkingAddressValidator.isValid(parkingAddress, null)).isFalse();
    }
}