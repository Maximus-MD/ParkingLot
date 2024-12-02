package com.endava.md.internship.parkinglot.controller;

import com.endava.md.internship.parkinglot.dto.ParkingLotGeneralDetailsDto;
import com.endava.md.internship.parkinglot.dto.ParkingLotRequestDto;
import com.endava.md.internship.parkinglot.dto.ParkingLotResponseDto;
import com.endava.md.internship.parkinglot.service.ParkingLotService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/parking-lots")
@RequiredArgsConstructor
public class ParkingLotController {

    private final ParkingLotService parkingService;

    @PostMapping("/create")
    public ResponseEntity<ParkingLotResponseDto> newParkingLot(@Valid @RequestBody final ParkingLotRequestDto parkingLotRequestDto) {
        ParkingLotResponseDto response = parkingService.createParkingLot(parkingLotRequestDto);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{name}")
    public ResponseEntity<ParkingLotResponseDto> removeParkingLot(@PathVariable String name) {
        ParkingLotResponseDto response = parkingService.deleteParkingLot(name);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ParkingLotGeneralDetailsDto>> getAllParkingLots() {
        List<ParkingLotGeneralDetailsDto> parkingLots = parkingService.getAllParkingLots();
        return ResponseEntity.ok(parkingLots);
    }
}