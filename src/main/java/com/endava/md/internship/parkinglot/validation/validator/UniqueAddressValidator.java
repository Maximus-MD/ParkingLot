package com.endava.md.internship.parkinglot.validation.validator;

import com.endava.md.internship.parkinglot.repository.ParkingLotRepository;
import com.endava.md.internship.parkinglot.validation.annotation.UniqueAddress;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UniqueAddressValidator implements ConstraintValidator<UniqueAddress, String> {
    private final ParkingLotRepository parkingLotRepository;

    @Override
    public boolean isValid(String address, ConstraintValidatorContext context) {
        return parkingLotRepository.findByAddress(address).isEmpty();
    }
}