package com.endava.md.internship.parkinglot.service;

import com.endava.md.internship.parkinglot.dto.ParkingSpotTypeDto;
import com.endava.md.internship.parkinglot.dto.ParkingSpotResponseDto;

import java.util.List;

public interface ParkingSpotService {
    ParkingSpotResponseDto changeParkingSpotType(Long id, ParkingSpotTypeDto parkingSpotTypeDto);

    List<String> getAllParkingSpotTypes();
}
