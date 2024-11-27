package com.endava.md.internship.parkinglot.validation;

import com.endava.md.internship.parkinglot.model.ParkingLot;
import com.endava.md.internship.parkinglot.repository.ParkingLotRepository;
import com.endava.md.internship.parkinglot.validation.validator.UniqueParkingNameValidator;
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
class CustomUniqueParkingNameValidatorTest {

    @Mock
    private ParkingLotRepository parkingLotRepository;

    @InjectMocks
    UniqueParkingNameValidator uniqueParkingNameValidator;

    @Test
    void isValid_ParkingNameIsUnique_ReturnsTrue() {
        String parkingName = "uniqueParkingName";
        when(parkingLotRepository.findByName(parkingName)).thenReturn(Optional.empty());

        assertTrue(uniqueParkingNameValidator.isValid(parkingName, null));
    }

    @Test
    void isValid_ParkingNameIsNotUnique_ReturnsFalse() {
        String parkingName = "notUniquesParkingName";
        when(parkingLotRepository.findByName(parkingName)).thenReturn(Optional.of(new ParkingLot()));

        assertFalse(uniqueParkingNameValidator.isValid(parkingName, null));
    }
}