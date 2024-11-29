package com.endava.md.internship.parkinglot.validation.validator;

import com.endava.md.internship.parkinglot.repository.ParkingLotRepository;
import com.endava.md.internship.parkinglot.validation.annotation.UniqueParkingName;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UniqueParkingNameValidator implements ConstraintValidator<UniqueParkingName, String> {
    private final ParkingLotRepository parkingLotRepository;

    @Override
    public boolean isValid(String name, ConstraintValidatorContext context) {
        return !parkingLotRepository.existsByName(name);
    }
}