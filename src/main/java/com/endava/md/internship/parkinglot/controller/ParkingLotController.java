package com.endava.md.internship.parkinglot.controller;

import com.endava.md.internship.parkinglot.dto.ParkingLotRequestDto;
import com.endava.md.internship.parkinglot.dto.ParkingLotResponseDto;
import com.endava.md.internship.parkinglot.service.ParkingLotService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/parking-lots")
@RequiredArgsConstructor
public class ParkingLotController {

    private final ParkingLotService parkingService;

    @PostMapping("/create")
    public ResponseEntity<ParkingLotResponseDto> newParkingLot(@RequestBody final ParkingLotRequestDto parkingLotRequestDto) {
        ParkingLotResponseDto response = parkingService.createParkingLot(parkingLotRequestDto);

        return ResponseEntity.ok(response);
    }
}