package com.endava.md.internship.parkinglot.validation;

import com.endava.md.internship.parkinglot.model.ParkingLot;
import com.endava.md.internship.parkinglot.repository.ParkingLotRepository;
import com.endava.md.internship.parkinglot.validation.validator.UniqueParkingAddressValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
        when(parkingLotRepository.findByAddress(parkingAddress)).thenReturn(Optional.empty());

        assertTrue(uniqueParkingAddressValidator.isValid(parkingAddress, null));
    }

    @Test
    void isValid_ParkingAddressIsNotUnique_ReturnsFalse() {
        String parkingAddress = "notUniquesParkingAddress";
        when(parkingLotRepository.findByAddress(parkingAddress)).thenReturn(Optional.of(new ParkingLot()));

        assertFalse(uniqueParkingAddressValidator.isValid(parkingAddress, null));
    }
}