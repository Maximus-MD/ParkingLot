package com.endava.md.internship.parkinglot.service.impl;

import com.endava.md.internship.parkinglot.dto.ParkingLotGeneralDetailsDto;
import com.endava.md.internship.parkinglot.dto.ParkingLotRequestDto;
import com.endava.md.internship.parkinglot.dto.ParkingLotResponseDto;
import com.endava.md.internship.parkinglot.dto.WorkingDayDto;
import com.endava.md.internship.parkinglot.exception.ParkingLotException;
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

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

        return buildParkingLotResponseDto();
    }

    public List<ParkingLevel> createParkingLevels(Map<String, Integer> spots, ParkingLot parkingLot) {
        return spots.entrySet().stream()
                .map(entry -> buildParkingLevel(entry.getKey(), entry.getValue(), parkingLot)).toList();
    }

    public List<ParkingSpot> createParkingSpots(ParkingLevel parkingLevel, int numberOfSpots) {
        return Stream.iterate(1, i -> i + 1)
                .limit(numberOfSpots)
                .map(i -> buildParkingSpot(parkingLevel, i))
                .toList();
    }

    @Override
    @Transactional
    public ParkingLotResponseDto deleteParkingLot(String name){
        ParkingLot parkingLot = parkingLotRepository.findByName(name)
                .orElseThrow(() -> new ParkingLotException(String.format("Parking lot %s not found.", name)));

        parkingLotRepository.delete(parkingLot);
        return buildParkingLotResponseDto();
    }

    private ParkingLevel buildParkingLevel(String levelName, Integer spotNumber, ParkingLot parkingLot) {
        ParkingLevel parkingLevel = new ParkingLevel();

        parkingLevel.setLevelName(levelName);
        parkingLevel.setParkingLot(parkingLot);

        ParkingLevel savedParkingLevel = parkingLevelRepository.save(parkingLevel);

        List<ParkingSpot> parkingSpots = createParkingSpots(savedParkingLevel, spotNumber);
        savedParkingLevel.setParkingSpots(parkingSpots);

        return savedParkingLevel;
    }

    private ParkingSpot buildParkingSpot(ParkingLevel parkingLevel, int index) {
        ParkingSpot parkingSpot = new ParkingSpot();

        parkingSpot.setParkingLevel(parkingLevel);
        parkingSpot.setOccupied(false);
        parkingSpot.setType(REGULAR);
        parkingSpot.setName(parkingLevel.getLevelName() + "-" + String.format("%03d", index));

        return parkingSpot;
    }

    private ParkingLotResponseDto buildParkingLotResponseDto() {
        return ParkingLotResponseDto.builder()
                .success(true)
                .error(Collections.emptySet())
                .build();
    }

    private ParkingLot convertToParkingLot(ParkingLotRequestDto parkingLotDto) {
        ParkingLot parkingLot = ParkingLot.builder()
                .name(parkingLotDto.name())
                .address(parkingLotDto.address())
                .operatesNonStop(parkingLotDto.operatesNonStop())
                .temporaryClosed(parkingLotDto.temporaryClosed())
                .build();

        if(!parkingLotDto.operatesNonStop()) {
            parkingLot.setStartTime(parkingLotDto.startTime());
            parkingLot.setEndTime(parkingLotDto.endTime());
        }

        ParkingLot savedParkingLot = parkingLotRepository.save(parkingLot);

        List<WorkingDay> workingTimes = convertToWorkingTime(parkingLotDto.workingDays());
        savedParkingLot.setWorkingDays(workingTimes);

        return savedParkingLot;
    }

    private List<WorkingDay> convertToWorkingTime(List<WorkingDayDto> workingDayDto) {
        return workingDayDto.stream()
                .map(dto -> workingDayRepository.findByDayName(dto.day()).orElseThrow(
                        () -> new ParkingLotException(String.format("Day %s not found.", dto.day()))
                )).toList();
    }

    @Override
    public List<ParkingLotGeneralDetailsDto> getAllParkingLots() {
        List<ParkingLot> parkingLots = parkingLotRepository.findAll();
        return parkingLots.stream()
                .map(this::convertToParkingLotGeneralDetailsDto)
                .toList();
    }

    private ParkingLotGeneralDetailsDto convertToParkingLotGeneralDetailsDto(ParkingLot parkingLot) {
        String operatingHours = "";
        String operatingDays = "";

        if (!parkingLot.isTemporaryClosed() && !parkingLot.isOperatesNonStop()) {
            operatingHours = String.format("%tR - %tR", parkingLot.getStartTime(), parkingLot.getEndTime());
            operatingDays = formatOperatingDays(parkingLot.getWorkingDays());
        }

        return new ParkingLotGeneralDetailsDto(
                parkingLot.getParkingLotId(),
                parkingLot.getName(),
                operatingHours,
                operatingDays,
                parkingLot.isTemporaryClosed(),
                parkingLot.isOperatesNonStop()
        );
    }

    private String formatOperatingDays(List<WorkingDay> workingDays) {
        if (workingDays == null || workingDays.isEmpty()) {
            return "";
        }
        return workingDays.stream()
                .map(day -> day.getDayName().name())
                .collect(Collectors.joining("/"));
    }
}