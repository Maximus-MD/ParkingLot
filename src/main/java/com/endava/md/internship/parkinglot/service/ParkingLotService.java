package com.endava.md.internship.parkinglot.service;

import com.endava.md.internship.parkinglot.dto.ParkingLotGeneralDetailsDto;
import com.endava.md.internship.parkinglot.dto.ParkingLotRequestDto;
import com.endava.md.internship.parkinglot.dto.ParkingLotResponseDto;

import java.util.List;

public interface ParkingLotService {
    ParkingLotResponseDto createParkingLot(ParkingLotRequestDto parkingLotDto);
    ParkingLotResponseDto deleteParkingLot(String name);
    List<ParkingLotGeneralDetailsDto> getAllParkingLots();
    ParkingLotResponseDto addUserToParkingLot(Long userId, Long parkingLotId);
    ParkingLotResponseDto removeUserFromParkingLot(Long userId, Long parkingLotId);
}