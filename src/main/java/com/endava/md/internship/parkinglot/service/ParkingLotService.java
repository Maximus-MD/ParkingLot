package com.endava.md.internship.parkinglot.service;

import com.endava.md.internship.parkinglot.dto.ParkingLotRequestDto;
import com.endava.md.internship.parkinglot.dto.ParkingLotResponseDto;

public interface ParkingLotService {
    ParkingLotResponseDto createParkingLot(ParkingLotRequestDto parkingLotDto);
}
