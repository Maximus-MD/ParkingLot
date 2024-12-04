package com.endava.md.internship.parkinglot.service.impl;

import com.endava.md.internship.parkinglot.dto.ParkingSpotResponseDto;
import com.endava.md.internship.parkinglot.dto.ParkingSpotTypeDto;
import com.endava.md.internship.parkinglot.exception.OccupiedParkingSpotException;
import com.endava.md.internship.parkinglot.exception.ParkingSpotNotFoundException;
import com.endava.md.internship.parkinglot.model.ParkingSpot;
import com.endava.md.internship.parkinglot.model.ParkingSpotType;
import com.endava.md.internship.parkinglot.repository.ParkingSpotRepository;
import com.endava.md.internship.parkinglot.service.ParkingSpotService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class ParkingSpotServiceImpl implements ParkingSpotService {

    private final ParkingSpotRepository parkingSpotRepository;

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
}
