package com.endava.md.internship.parkinglot.service;

import com.endava.md.internship.parkinglot.dto.ParkingSpotTypeDto;
import com.endava.md.internship.parkinglot.dto.ParkingSpotResponseDto;

public interface ParkingSpotService {
    ParkingSpotResponseDto changeParkingSpotType(Long id, ParkingSpotTypeDto parkingSpotTypeDto);
}
