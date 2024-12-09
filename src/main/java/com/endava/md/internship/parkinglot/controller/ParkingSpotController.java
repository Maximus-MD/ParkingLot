package com.endava.md.internship.parkinglot.controller;

import com.endava.md.internship.parkinglot.dto.ParkingSpotTypeDto;
import com.endava.md.internship.parkinglot.dto.ParkingSpotResponseDto;
import com.endava.md.internship.parkinglot.service.ParkingSpotService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/parking-spots")
@RequiredArgsConstructor
public class ParkingSpotController {

    private final ParkingSpotService parkingSpotService;

    @PatchMapping("/change-type/{id}")
    public ResponseEntity<ParkingSpotResponseDto> changeParkingSpotType(
            @PathVariable("id") Long id,
            @Valid @RequestBody ParkingSpotTypeDto parkingSpotTypeDto
    ) {
        ParkingSpotResponseDto parkingSpotResponseDto =
                parkingSpotService.changeParkingSpotType(id, parkingSpotTypeDto);

        return ResponseEntity.status(HttpStatus.OK).body(parkingSpotResponseDto);
    }

}
