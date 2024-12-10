package com.endava.md.internship.parkinglot.service.impl;

import com.endava.md.internship.parkinglot.dto.ParkingLotGeneralDetailsDto;
import com.endava.md.internship.parkinglot.dto.ParkingLotRequestDto;
import com.endava.md.internship.parkinglot.dto.ParkingLotResponseDto;
import com.endava.md.internship.parkinglot.dto.WorkingDayDto;
import com.endava.md.internship.parkinglot.exception.EmailSendException;
import com.endava.md.internship.parkinglot.exception.ParkingLotException;
import com.endava.md.internship.parkinglot.exception.UserAlreadyAssignedException;
import com.endava.md.internship.parkinglot.exception.UserNotAssignedException;
import com.endava.md.internship.parkinglot.exception.UserNotFoundException;
import com.endava.md.internship.parkinglot.model.ParkingLevel;
import com.endava.md.internship.parkinglot.model.ParkingLot;
import com.endava.md.internship.parkinglot.model.ParkingLotUser;
import com.endava.md.internship.parkinglot.model.ParkingLotUserId;
import com.endava.md.internship.parkinglot.model.ParkingSpot;
import com.endava.md.internship.parkinglot.model.User;
import com.endava.md.internship.parkinglot.model.WorkingDay;
import com.endava.md.internship.parkinglot.repository.ParkingLevelRepository;
import com.endava.md.internship.parkinglot.repository.ParkingLotRepository;
import com.endava.md.internship.parkinglot.repository.ParkingLotUserRepository;
import com.endava.md.internship.parkinglot.repository.ParkingSpotRepository;
import com.endava.md.internship.parkinglot.repository.UserRepository;
import com.endava.md.internship.parkinglot.repository.WorkingDayRepository;
import com.endava.md.internship.parkinglot.service.EmailSenderService;
import com.endava.md.internship.parkinglot.service.ParkingLotService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.endava.md.internship.parkinglot.model.ParkingSpotType.REGULAR;

@Service
@RequiredArgsConstructor
public class ParkingLotServiceImpl implements ParkingLotService {

    private static final Logger log = LoggerFactory.getLogger(ParkingLotServiceImpl.class);

    private final ParkingLotRepository parkingLotRepository;

    private final ParkingLevelRepository parkingLevelRepository;

    private final WorkingDayRepository workingDayRepository;

    private final UserRepository userRepository;

    private final ParkingLotUserRepository parkingLotUserRepository;

    private final EmailSenderService emailSenderService;

    private final ParkingSpotRepository parkingSpotRepository;

    @Override
    @Transactional
    public ParkingLotResponseDto createParkingLot(ParkingLotRequestDto parkingLotDto) {
        ParkingLot savedParkingLot = convertToParkingLot(parkingLotDto);

        List<ParkingLevel> parkingLevels = createParkingLevels(parkingLotDto.parkingLevels(), savedParkingLot);
        savedParkingLot.setParkingLevels(parkingLevels);

        return buildParkingLotResponseDto();
    }

    @Override
    @Transactional
    public ParkingLotResponseDto deleteParkingLot(String name){
        ParkingLot parkingLot = parkingLotRepository.findByName(name)
                .orElseThrow(() -> new ParkingLotException(String.format("Parking lot %s not found.", name)));

        parkingLotRepository.delete(parkingLot);
        return buildParkingLotResponseDto();
    }

    @Override
    public List<ParkingLotGeneralDetailsDto> getAllParkingLots() {
        return parkingLotRepository.findAll().stream()
                .map(this::convertToParkingLotGeneralDetailsDto)
                .toList();
    }

    @Transactional
    @Override
    public ParkingLotResponseDto removeUserFromParkingLot(Long userId, Long parkingLotId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found."));
        ParkingLot parkingLot = parkingLotRepository.findById(parkingLotId)
                .orElseThrow(() -> new ParkingLotException("Parking lot not found."));

        ParkingLotUserId parkingLotUserId = new ParkingLotUserId(userId, parkingLotId);
        ParkingLotUser parkingLotUser = parkingLotUserRepository.findById(parkingLotUserId)
                .orElseThrow(() -> new UserNotAssignedException("User is not assigned to this parking lot."));

        parkingLotUserRepository.delete(parkingLotUser);

        String subject = "You have been removed from Parking Lot";
        String message = "You have been removed from " + parkingLot.getName();
        try {
            emailSenderService.sendEmail(user.getEmail(), subject, message);
        } catch (MessagingException e) {
            throw new EmailSendException("Failed to send email.");
        }

        return ParkingLotResponseDto.builder()
                .success(true)
                .error(Collections.emptySet())
                .build();
    }

    @Transactional
    @Override
    public ParkingLotResponseDto addUserToParkingLot(Long userId, Long parkingLotId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found."));
        ParkingLot parkingLot = parkingLotRepository.findById(parkingLotId)
                .orElseThrow(() -> new ParkingLotException("Parking lot not found."));

        ParkingLotUserId parkingLotUserId = new ParkingLotUserId(userId, parkingLotId);
        if (parkingLotUserRepository.existsById(parkingLotUserId)) {
            throw new UserAlreadyAssignedException("User is already added to this parking lot.");
        }

        ParkingLotUser parkingLotUser = createAndSaveParkingLotUser(parkingLotUserId, user, parkingLot);
        log.info("User assigned to parking lot: {}", parkingLotUser);

        String subject = "You have been added to Parking Lot";
        String message = "You have been added to " + parkingLot.getName();
        try {
            emailSenderService.sendEmail(user.getEmail(), subject, message);
        } catch (MessagingException e) {
            throw new EmailSendException("Failed to send email.");
        }

        return ParkingLotResponseDto.builder()
                .success(true)
                .error(Collections.emptySet())
                .build();
    }

    public List<ParkingLevel> createParkingLevels(Map<String, Integer> spots, ParkingLot parkingLot) {
        return spots.entrySet().stream()
                .map(entry -> buildParkingLevel(entry.getKey(), entry.getValue(), parkingLot)).toList();
    }

    protected static ParkingLotResponseDto buildParkingLotResponseDto() {
        return ParkingLotResponseDto.builder()
                .success(true)
                .error(Collections.emptySet())
                .build();
    }

    private List<ParkingSpot> createParkingSpots(ParkingLevel parkingLevel, int numberOfSpots) {
        return Stream.iterate(1, i -> i + 1)
                .limit(numberOfSpots)
                .map(i -> buildParkingSpot(parkingLevel, i))
                .toList();
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

    private ParkingLotGeneralDetailsDto convertToParkingLotGeneralDetailsDto(ParkingLot parkingLot) {
        String operatingHours = "";
        List<String> operatingDays = Collections.emptyList();

        if (formatOperatingHours(parkingLot)) {
            operatingHours = String.format("%tR - %tR", parkingLot.getStartTime(), parkingLot.getEndTime());
            operatingDays = formatOperatingDays(parkingLot.getWorkingDays());
        }

        Pair<Integer, Integer> spotsInfo = calculateSpots(parkingLot);
        int totalSpots = spotsInfo.getLeft();
        int occupiedSpots = spotsInfo.getRight();

        double loadPercentage = totalSpots > 0 ? (occupiedSpots * 100.0) / totalSpots : 0.0;

        return new ParkingLotGeneralDetailsDto(
                parkingLot.getParkingLotId(),
                parkingLot.getName(),
                operatingHours,
                operatingDays,
                parkingLot.isTemporaryClosed(),
                parkingLot.isOperatesNonStop(),
                totalSpots,
                totalSpots - occupiedSpots,
                loadPercentage
        );
    }

    private boolean formatOperatingHours(ParkingLot parkingLot) {
        return !parkingLot.isTemporaryClosed() && !parkingLot.isOperatesNonStop();
    }

    private Pair<Integer, Integer> calculateSpots(ParkingLot parkingLot) {
        return Optional.ofNullable(parkingLot.getParkingLevels())
                .map(this::calculateSpotsCount)
                .orElseGet(() -> new ImmutablePair<>(0, 0));
    }

    private Pair<Integer, Integer> calculateSpotsCount(List<ParkingLevel> levels) {
        int totalSpotsCounter = levels.stream()
                .flatMap(level -> Optional.ofNullable(level.getParkingSpots())
                        .map(List::stream)
                        .orElseGet(Stream::empty))
                .mapToInt(s -> 1)
                .sum();

        int occupiedSpotsCounter = levels.stream()
                .mapToInt(level -> parkingSpotRepository.countByParkingLevelAndOccupied(level, true))
                .sum();

        return new ImmutablePair<>(totalSpotsCounter, occupiedSpotsCounter);
    }

    private List<String> formatOperatingDays(List<WorkingDay> workingDays) {
        if (workingDays == null || workingDays.isEmpty()) {
            return Collections.emptyList();
        }
        return workingDays.stream()
                .map(day -> day.getDayName().name())
                .collect(Collectors.toList());
    }

    private ParkingLotUser createAndSaveParkingLotUser(ParkingLotUserId parkingLotUserId, User user, ParkingLot parkingLot) {
        ParkingLotUser parkingLotUser = new ParkingLotUser();
        parkingLotUser.setId(parkingLotUserId);
        parkingLotUser.setUser(user);
        parkingLotUser.setParkingLot(parkingLot);
        return parkingLotUserRepository.save(parkingLotUser);
    }
}