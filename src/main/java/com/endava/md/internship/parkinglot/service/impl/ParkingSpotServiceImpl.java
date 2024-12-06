package com.endava.md.internship.parkinglot.service.impl;

import com.endava.md.internship.parkinglot.dto.ParkingLotResponseDto;
import com.endava.md.internship.parkinglot.dto.ParkingSpotDto;
import com.endava.md.internship.parkinglot.exception.CustomAuthException;
import com.endava.md.internship.parkinglot.dto.ParkingSpotResponseDto;
import com.endava.md.internship.parkinglot.dto.ParkingSpotTypeDto;
import com.endava.md.internship.parkinglot.exception.OccupiedParkingSpotException;
import com.endava.md.internship.parkinglot.exception.ParkingSpotNotFoundException;
import com.endava.md.internship.parkinglot.exception.ParkingSpotOccupiedException;
import com.endava.md.internship.parkinglot.exception.UserAlreadyHasParkingSpotException;
import com.endava.md.internship.parkinglot.model.ParkingSpot;
import com.endava.md.internship.parkinglot.model.User;
import com.endava.md.internship.parkinglot.model.ParkingSpotType;
import com.endava.md.internship.parkinglot.repository.ParkingSpotRepository;
import com.endava.md.internship.parkinglot.repository.UserRepository;
import com.endava.md.internship.parkinglot.service.ParkingSpotService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import static com.endava.md.internship.parkinglot.exception.AuthErrorTypeEnum.USER_NOT_FOUND;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ParkingSpotServiceImpl implements ParkingSpotService {

    private final ParkingSpotRepository parkingSpotRepository;

    private final UserRepository userRepository;

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public ParkingLotResponseDto occupyParkingSpot(ParkingSpotDto parkingSpotDto) {

        ParkingSpot parkingSpot = parkingSpotRepository.findByNameAndParkingName(parkingSpotDto.spotName(), parkingSpotDto.parkingName())
                .orElseThrow(() -> new ParkingSpotNotFoundException(
                        String.format("Parking spot %s not found in parking lot %s.", parkingSpotDto.spotName(), parkingSpotDto.parkingName())));

        User user = userRepository.findByEmailIgnoreCase(parkingSpotDto.email())
                .orElseThrow(() -> new CustomAuthException(USER_NOT_FOUND, String.format("User with email %s not found", parkingSpotDto.email())));

        parkingSpotRepository.findByUser_UserId(user.getUserId())
                .ifPresent(exist -> {
                    throw new UserAlreadyHasParkingSpotException(
                            String.format("User %s has already %s parking spot.", parkingSpotDto.email(), exist.getName()));
                });

        if (parkingSpot.isOccupied()) {
            throw new ParkingSpotOccupiedException(String.format("Parking spot %s is already occupied.", parkingSpotDto.spotName()));
        }

        parkingSpot.setOccupied(true);
        parkingSpot.setUser(user);
        parkingSpotRepository.save(parkingSpot);

        return buildParkingLotResponseDto();
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public ParkingSpotResponseDto changeParkingSpotType(Long id, ParkingSpotTypeDto parkingSpotTypeDto) {
        ParkingSpot parkingSpot = parkingSpotRepository.findBySpotId(id).orElseThrow(
                () -> new ParkingSpotNotFoundException("Parking Spot is Not Found!")
        );

        if (parkingSpot.isOccupied()) {
            throw new OccupiedParkingSpotException("Parking Spot is Occupied!");
        }

        ParkingSpotType newParkingSpotType = ParkingSpotType.valueOf(parkingSpotTypeDto.parkingSpotType().toUpperCase());

        if (parkingSpot.getType().equals(newParkingSpotType)) {
            return convertToParkingSpotResponseDto(parkingSpot);
        }

        parkingSpot.setType(ParkingSpotType.valueOf(parkingSpotTypeDto.parkingSpotType().toUpperCase()));
        parkingSpotRepository.save(parkingSpot);

        return convertToParkingSpotResponseDto(parkingSpot);
    }

    private ParkingSpotResponseDto convertToParkingSpotResponseDto(ParkingSpot parkingSpot) {
        return new ParkingSpotResponseDto(
                parkingSpot.getSpotId(),
                parkingSpot.getName(),
                parkingSpot.getType(),
                parkingSpot.isOccupied(),
                parkingSpot.getParkingLevel().getLevelId()
        );

    }

    public List<String> getAllParkingSpotTypes() {
        return Arrays.stream(ParkingSpotType.values()).map(Enum::name).toList();
    }
}
