package com.endava.md.internship.parkinglot.validation.validator;

import com.endava.md.internship.parkinglot.repository.ParkingLotRepository;
import com.endava.md.internship.parkinglot.validation.annotation.UniqueParkingAddress;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UniqueParkingAddressValidator implements ConstraintValidator<UniqueParkingAddress, String> {
    private final ParkingLotRepository parkingLotRepository;

    @Override
    public boolean isValid(String address, ConstraintValidatorContext context) {
        return !parkingLotRepository.existsByAddress(address);
    }
}