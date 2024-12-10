package com.endava.md.internship.parkinglot.controller;

import com.endava.md.internship.parkinglot.dto.ParkingLotResponseDto;
import com.endava.md.internship.parkinglot.dto.ParkingSpotDto;
import com.endava.md.internship.parkinglot.dto.ParkingSpotTypeDto;
import com.endava.md.internship.parkinglot.dto.ParkingSpotResponseDto;
import com.endava.md.internship.parkinglot.service.ParkingSpotService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/parking-spots")
@RequiredArgsConstructor
public class ParkingSpotController {

    private final ParkingSpotService parkingSpotService;

    @PatchMapping("/reserve-spot")
    public ResponseEntity<ParkingLotResponseDto> reserveParkingSpot(@Valid @RequestBody ParkingSpotDto parkingSpotDto){
        ParkingLotResponseDto response = parkingSpotService.occupyParkingSpot(parkingSpotDto);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/change-type/{id}")
    public ResponseEntity<ParkingSpotResponseDto> changeParkingSpotType(
            @PathVariable("id") Long id,
            @Valid @RequestBody ParkingSpotTypeDto parkingSpotTypeDto
    ) {
        ParkingSpotResponseDto parkingSpotResponseDto =
                parkingSpotService.changeParkingSpotType(id, parkingSpotTypeDto);

        return ResponseEntity.status(HttpStatus.OK).body(parkingSpotResponseDto);
    }

    @GetMapping("/types")
    public ResponseEntity<List<String>> getAllParkingSpotTypes() {
        return ResponseEntity.status(HttpStatus.OK).body(parkingSpotService.getAllParkingSpotTypes());
    }

}
