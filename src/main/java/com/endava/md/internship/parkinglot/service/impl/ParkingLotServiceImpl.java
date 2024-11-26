package com.endava.md.internship.parkinglot.service.impl;

import com.endava.md.internship.parkinglot.dto.ParkingLotRequestDto;
import com.endava.md.internship.parkinglot.dto.ParkingLotResponseDto;
import com.endava.md.internship.parkinglot.dto.WorkingDayDto;
import com.endava.md.internship.parkinglot.model.ParkingLevel;
import com.endava.md.internship.parkinglot.model.ParkingLot;
import com.endava.md.internship.parkinglot.model.ParkingSpot;
import com.endava.md.internship.parkinglot.model.WorkingDay;
import com.endava.md.internship.parkinglot.repository.ParkingLevelRepository;
import com.endava.md.internship.parkinglot.repository.ParkingLotRepository;
import com.endava.md.internship.parkinglot.repository.WorkingDayRepository;
import com.endava.md.internship.parkinglot.service.ParkingLotService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.endava.md.internship.parkinglot.model.ParkingSpotType.REGULAR;

@Service
@RequiredArgsConstructor
public class ParkingLotServiceImpl implements ParkingLotService {

    private final ParkingLotRepository parkingLotRepository;

    private final ParkingLevelRepository parkingLevelRepository;

    private final WorkingDayRepository workingDayRepository;

    @Override
    @Transactional
    public ParkingLotResponseDto createParkingLot(ParkingLotRequestDto parkingLotDto) {
        ParkingLot savedParkingLot = convertToParkingLot(parkingLotDto);

        List<ParkingLevel> parkingLevels = createParkingLevels(parkingLotDto.parkingLevels(), savedParkingLot);
        savedParkingLot.setParkingLevels(parkingLevels);

        return new ParkingLotResponseDto(
                savedParkingLot.getName(),
                savedParkingLot.getAddress(),
                savedParkingLot.getStartTime(),
                savedParkingLot.getEndTime(),
                savedParkingLot.getOperatesNonStop(),
                savedParkingLot.getTemporaryClosed(),
                true);
    }

    public List<ParkingLevel> createParkingLevels(Map<String, Integer> spots, ParkingLot parkingLot) {
        return spots.entrySet().stream()
                .map(entry -> {
                    String levelName = entry.getKey();
                    Integer spotNumber = entry.getValue();

                    ParkingLevel parkingLevel = new ParkingLevel();
                    parkingLevel.setLevelName(levelName);
                    parkingLevel.setParkingLot(parkingLot);

                    ParkingLevel savedParkingLevel = parkingLevelRepository.save(parkingLevel);

                    List<ParkingSpot> parkingSpots = createParkingSpots(savedParkingLevel, spotNumber);
                    savedParkingLevel.setParkingSpots(parkingSpots);

                    return savedParkingLevel;
                }).toList();
    }

    public List<ParkingSpot> createParkingSpots(ParkingLevel parkingLevel, int numberOfSpots) {
        List<ParkingSpot> parkingSpots = new ArrayList<>();

        for (int i = 1; i <= numberOfSpots; i++) {
            ParkingSpot parkingSpot = new ParkingSpot();

            parkingSpot.setParkingLevel(parkingLevel);
            parkingSpot.setOccupied(false);
            parkingSpot.setType(REGULAR);
            parkingSpot.setName(parkingLevel.getLevelName() + "-" + String.format("%03d", i));

            parkingSpots.add(parkingSpot);
        }

        return parkingSpots;
    }

    private ParkingLot convertToParkingLot(ParkingLotRequestDto parkingLotDto) {
        ParkingLot parkingLot = ParkingLot.builder()
                .name(parkingLotDto.name())
                .address(parkingLotDto.address())
                .startTime(parkingLotDto.startTime())
                .endTime(parkingLotDto.endTime())
                .operatesNonStop(parkingLotDto.operatesNonStop())
                .temporaryClosed(parkingLotDto.temporaryClosed())
                .build();

        ParkingLot savedParkingLot = parkingLotRepository.save(parkingLot);

        List<WorkingDay> workingTimes = convertToWorkingTime(parkingLotDto.workingTimes());
        savedParkingLot.setWorkingDays(workingTimes);

        return savedParkingLot;
    }

    private List<WorkingDay> convertToWorkingTime(List<WorkingDayDto> workingDayDto) {
        return workingDayDto.stream()
                .map(dto -> workingDayRepository.findByDayName(dto.day()).orElseThrow())
                .toList();
    }
}