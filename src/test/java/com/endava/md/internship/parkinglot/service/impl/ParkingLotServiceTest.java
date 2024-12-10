package com.endava.md.internship.parkinglot.service.impl;

import com.endava.md.internship.parkinglot.dto.ParkingLotGeneralDetailsDto;
import com.endava.md.internship.parkinglot.dto.ParkingLotRequestDto;
import com.endava.md.internship.parkinglot.dto.ParkingLotResponseDto;
import com.endava.md.internship.parkinglot.exception.ParkingLotException;
import com.endava.md.internship.parkinglot.model.ParkingLevel;
import com.endava.md.internship.parkinglot.model.ParkingLot;
import com.endava.md.internship.parkinglot.model.ParkingSpot;
import com.endava.md.internship.parkinglot.model.WorkingDay;
import com.endava.md.internship.parkinglot.repository.ParkingLevelRepository;
import com.endava.md.internship.parkinglot.repository.ParkingLotRepository;
import com.endava.md.internship.parkinglot.repository.ParkingSpotRepository;
import com.endava.md.internship.parkinglot.repository.WorkingDayRepository;
import com.endava.md.internship.parkinglot.utils.ParkingLotDTOUtils;
import com.endava.md.internship.parkinglot.utils.ParkingLotUtils;
import com.endava.md.internship.parkinglot.exception.EmailSendException;
import com.endava.md.internship.parkinglot.exception.UserAlreadyAssignedException;
import com.endava.md.internship.parkinglot.exception.UserNotAssignedException;
import com.endava.md.internship.parkinglot.exception.UserNotFoundException;
import com.endava.md.internship.parkinglot.model.ParkingLotUser;
import com.endava.md.internship.parkinglot.model.ParkingLotUserId;
import com.endava.md.internship.parkinglot.model.User;
import com.endava.md.internship.parkinglot.repository.ParkingLotUserRepository;
import com.endava.md.internship.parkinglot.repository.UserRepository;
import com.endava.md.internship.parkinglot.service.EmailSenderService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Time;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.endava.md.internship.parkinglot.utils.ParkingLotDTOUtils.getPreparedParkingLotRequestDtoNullTime;
import static com.endava.md.internship.parkinglot.utils.ParkingLotDTOUtils.getPreparedParkingLotRequestDtoWithInvalidDay;
import static com.endava.md.internship.parkinglot.utils.ParkingLotUtils.getPreparedParkingLot;
import static com.endava.md.internship.parkinglot.utils.ParkingLotUtils.getPreparedParkingLotWithNullTime;
import static java.time.DayOfWeek.FRIDAY;
import static java.time.DayOfWeek.MONDAY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;

import jakarta.mail.MessagingException;
import org.junit.jupiter.api.BeforeEach;

@ExtendWith(MockitoExtension.class)
class ParkingLotServiceTest {

    @Mock
    private ParkingLotRepository parkingLotRepository;

    @Mock
    private ParkingLevelRepository parkingLevelRepository;

    @Mock
    private WorkingDayRepository workingDayRepository;

    @Mock
    private ParkingLotUserRepository parkingLotUserRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private EmailSenderService emailSenderService;

    @Mock
    private ParkingSpot parkingSpot;

    @Mock
    private ParkingSpotRepository parkingSpotRepository;

    @InjectMocks
    private ParkingLotServiceImpl parkingLotService;

    private User testUser;
    private ParkingLot testParkingLot;



    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setUserId(1L);
        testUser.setEmail("test@example.com");

        testParkingLot = new ParkingLot();
        testParkingLot.setParkingLotId(100L);
        testParkingLot.setName("Test Parking Lot");
    }

    @Test
    void testCreateParkingLot_WhenItDoesNotExist() {

        ParkingLotRequestDto parkingLotRequestDto = ParkingLotDTOUtils.getPreparedParkingLotRequestDto();

        when(workingDayRepository.findByDayName(MONDAY))
                .thenReturn(Optional.of(new WorkingDay(1L, MONDAY)));

        ParkingLot savedParkingLot = ParkingLotUtils.getPreparedParkingLot();
        when(parkingLotRepository.save(any(ParkingLot.class))).thenReturn(savedParkingLot);

        ParkingLevel savedParkingLevel = new ParkingLevel();
        savedParkingLevel.setLevelId(1L);
        savedParkingLevel.setLevelName("A");
        savedParkingLevel.setParkingLot(savedParkingLot);

        when(parkingLevelRepository.save(any(ParkingLevel.class))).thenReturn(savedParkingLevel);

        ParkingLotResponseDto response = parkingLotService.createParkingLot(parkingLotRequestDto);

        assertNotNull(response);
        assertTrue(response.success());

        verify(parkingLotRepository).save(any(ParkingLot.class));
        verify(parkingLevelRepository).save(any(ParkingLevel.class));
    }

    @Test
    void testWhenDayDoesNotExist_ThrowException() {
        ParkingLotRequestDto requestDto = getPreparedParkingLotRequestDtoWithInvalidDay();

        when(workingDayRepository.findByDayName(FRIDAY)).thenReturn(Optional.empty());

        ParkingLotException exception = assertThrows(ParkingLotException.class,
                () -> parkingLotService.createParkingLot(requestDto));

        assertEquals("Day FRIDAY not found.", exception.getMessage());
    }

    @Test
    void testCreateParkingLot_WhenOperatesNonStopIsTrue(){
        ParkingLotRequestDto requestDto = getPreparedParkingLotRequestDtoNullTime();

        when(workingDayRepository.findByDayName(MONDAY))
                .thenReturn(Optional.of(new WorkingDay(1L, MONDAY)));

        ParkingLot savedParkingLot = getPreparedParkingLotWithNullTime();

        when(parkingLotRepository.save(any(ParkingLot.class))).thenReturn(savedParkingLot);

        ParkingLevel savedParkingLevel = new ParkingLevel();
        savedParkingLevel.setLevelId(1L);
        savedParkingLevel.setLevelName("A");
        savedParkingLevel.setParkingLot(savedParkingLot);

        when(parkingLevelRepository.save(any(ParkingLevel.class))).thenReturn(savedParkingLevel);

        ParkingLotResponseDto response = parkingLotService.createParkingLot(requestDto);

        assertNotNull(response);
        assertTrue(response.success());

        assertNull(savedParkingLot.getStartTime());
        assertNull(savedParkingLot.getEndTime());
        assertTrue(savedParkingLot.isOperatesNonStop());

        verify(parkingLotRepository).save(any(ParkingLot.class));
        verify(parkingLevelRepository).save(any(ParkingLevel.class));
    }

    @Test
    void testDeleteParkingLot_WhenItExists(){
        ParkingLot parkingLot = getPreparedParkingLot();

        when(parkingLotRepository.findByName(parkingLot.getName())).thenReturn(Optional.of(parkingLot));

        parkingLotService.deleteParkingLot(parkingLot.getName());

        verify(parkingLotRepository).delete(parkingLot);
    }

    @Test
    void getAllParkingLots_ReturnsEmptyList_whenNoParkingLotsInDatabase() {
        when(parkingLotRepository.findAll()).thenReturn(new ArrayList<>());
        List<ParkingLotGeneralDetailsDto> result = parkingLotService.getAllParkingLots();
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void getAllParkingLots_ReturnsParkingLotDetails_whenParkingLotIsOperational() {
        ParkingLot lot = new ParkingLot();
        lot.setParkingLotId(1L);
        lot.setName("Endava Tower Parking Lot");
        lot.setStartTime(Time.valueOf("08:00:00"));
        lot.setEndTime(Time.valueOf("18:00:00"));
        lot.setWorkingDays(Arrays.asList(createWorkingDay(DayOfWeek.MONDAY), createWorkingDay(DayOfWeek.TUESDAY)));
        lot.setOperatesNonStop(false);
        lot.setTemporaryClosed(false);

        when(parkingLotRepository.findAll()).thenReturn(Collections.singletonList(lot));
        List<ParkingLotGeneralDetailsDto> result = parkingLotService.getAllParkingLots();
        assertNotNull(result);
        assertEquals(1, result.size());
        ParkingLotGeneralDetailsDto expectedDto = createParkingLotListDto(1L, "Endava Tower Parking Lot", "08:00 - 18:00", "MONDAY/TUESDAY", false, false, 0, 0, 0.0);
        assertEquals(expectedDto, result.get(0));
    }

    @Test
    void getAllParkingLots_ReturnsNonStopOperatingDetails_WhenParkingLotOperatesNonStop() {
        ParkingLot lot = new ParkingLot();
        lot.setParkingLotId(2L);
        lot.setName("Kaufland Parking Lot");
        lot.setOperatesNonStop(true);
        lot.setTemporaryClosed(false);

        when(parkingLotRepository.findAll()).thenReturn(Collections.singletonList(lot));
        List<ParkingLotGeneralDetailsDto> result = parkingLotService.getAllParkingLots();
        assertNotNull(result);
        assertEquals(1, result.size());
        ParkingLotGeneralDetailsDto expectedDto = createParkingLotListDto(2L, "Kaufland Parking Lot", "", "", false, true, 0, 0, 0.0);
        assertEquals(expectedDto, result.get(0));
    }

    @Test
    void getAll_whenParkingLots_ReturnsIsTemporarilyClosed_WhenParkingLotIsTemporaryClosed() {
        ParkingLot lot = new ParkingLot();
        lot.setParkingLotId(3L);
        lot.setName("N1 Hypermarket Parking Lot");
        lot.setTemporaryClosed(true);

        when(parkingLotRepository.findAll()).thenReturn(Collections.singletonList(lot));
        List<ParkingLotGeneralDetailsDto> result = parkingLotService.getAllParkingLots();
        assertNotNull(result);
        assertEquals(1, result.size());
        ParkingLotGeneralDetailsDto expectedDto = createParkingLotListDto(3L, "N1 Hypermarket Parking Lot", "", "", true, false, 0, 0, 0.0);
        assertEquals(expectedDto, result.get(0));
    }

    @Test
    void getAllParkingLots_ReturnsDTOsWithEmptyWorkingDays_WhenWorkingDaysAreNull() {
        ParkingLot lot = new ParkingLot();
        lot.setParkingLotId(4L);
        lot.setName("Empty Days Parking Lot");
        lot.setStartTime(Time.valueOf("08:00:00"));
        lot.setEndTime(Time.valueOf("18:00:00"));
        lot.setWorkingDays(null);
        lot.setOperatesNonStop(false);
        lot.setTemporaryClosed(false);

        when(parkingLotRepository.findAll()).thenReturn(Collections.singletonList(lot));
        List<ParkingLotGeneralDetailsDto> result = parkingLotService.getAllParkingLots();
        assertNotNull(result);
        assertEquals(1, result.size());
        ParkingLotGeneralDetailsDto expectedDto = createParkingLotListDto(4L, "Empty Days Parking Lot", "08:00 - 18:00", "", false, false, 0, 0, 0.0);
        assertEquals(expectedDto, result.get(0));
    }

    @Test
    void getAllParkingLots_CalculatesSpotsAndLoadPercentage() {
        ParkingLot lot = new ParkingLot();
        lot.setParkingLotId(5L);
        lot.setName("Endava Tower Parking Lot");
        lot.setOperatesNonStop(false);
        lot.setTemporaryClosed(false);

        ParkingLevel level = new ParkingLevel();
        level.setLevelName("L1");

        ParkingSpot spot1 = new ParkingSpot();
        spot1.setOccupied(false);
        ParkingSpot spot2 = new ParkingSpot();
        spot2.setOccupied(true);
        ParkingSpot spot3 = new ParkingSpot();
        spot3.setOccupied(false);
        level.setParkingSpots(Arrays.asList(spot1, spot2, spot3));
        lot.setParkingLevels(Collections.singletonList(level));

        when(parkingLotRepository.findAll()).thenReturn(Collections.singletonList(lot));

        when(parkingSpotRepository.countByParkingLevelAndOccupied(level, true)).thenReturn(1);

        List<ParkingLotGeneralDetailsDto> result = parkingLotService.getAllParkingLots();

        assertNotNull(result);
        assertEquals(1, result.size());
        ParkingLotGeneralDetailsDto dto = result.get(0);
        assertEquals(3, dto.totalSpots());
        assertEquals(2, dto.availableSpots()); // totalSpots - occupiedSpots
        assertEquals((1 * 100.0) / 3, dto.loadPercentage(), 0.001); // Check the load percentage calculation
    }


    private WorkingDay createWorkingDay(DayOfWeek dayOfWeek) {
        WorkingDay workingDay = new WorkingDay();
        workingDay.setDayName(dayOfWeek);
        return workingDay;
    }

    private ParkingLotGeneralDetailsDto createParkingLotListDto(Long id, String name, String workingHours, String workingDays, boolean isTemporaryClosed, boolean operatesNonStop, int totalSpots, int availableSpots, double loadPercentage) {
        List<String> daysList = workingDays.isEmpty() ? Collections.emptyList() : Arrays.asList(workingDays.split("/"));
        return new ParkingLotGeneralDetailsDto(
                id,
                name,
                workingHours,
                daysList,
                isTemporaryClosed,
                operatesNonStop,
                totalSpots,
                availableSpots,
                loadPercentage
        );
    }

    @Test
    void addUserToParkingLot_Success() throws MessagingException {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(parkingLotRepository.findById(100L)).thenReturn(Optional.of(testParkingLot));
        when(parkingLotUserRepository.existsById(new ParkingLotUserId(1L, 100L))).thenReturn(false);

        ParkingLotResponseDto response = parkingLotService.addUserToParkingLot(1L, 100L);

        assertTrue(response.success());
        assertEquals(Collections.emptySet(), response.error());

        verify(parkingLotUserRepository).save(any(ParkingLotUser.class));
        verify(emailSenderService).sendEmail("test@example.com", "You have been added to Parking Lot", "You have been added to Test Parking Lot");
    }

    @Test
    void addUserToParkingLot_UserNotFound() {
        when(userRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> parkingLotService.addUserToParkingLot(2L, 100L));
        verifyNoInteractions(emailSenderService);
    }

    @Test
    void addUserToParkingLot_ParkingLotNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(parkingLotRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(ParkingLotException.class, () -> parkingLotService.addUserToParkingLot(1L, 999L));
        verifyNoInteractions(emailSenderService);
    }

    @Test
    void addUserToParkingLot_UserAlreadyAssigned() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(parkingLotRepository.findById(100L)).thenReturn(Optional.of(testParkingLot));
        when(parkingLotUserRepository.existsById(new ParkingLotUserId(1L, 100L))).thenReturn(true);

        assertThrows(UserAlreadyAssignedException.class, () -> parkingLotService.addUserToParkingLot(1L, 100L));
        verifyNoInteractions(emailSenderService);
    }

    @Test
    void addUserToParkingLot_EmailSendError() throws MessagingException {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(parkingLotRepository.findById(100L)).thenReturn(Optional.of(testParkingLot));
        when(parkingLotUserRepository.existsById(new ParkingLotUserId(1L, 100L))).thenReturn(false);
        doThrow(new MessagingException("Send failed")).when(emailSenderService)
                .sendEmail(anyString(), anyString(), anyString());

        assertThrows(EmailSendException.class, () -> parkingLotService.addUserToParkingLot(1L, 100L));
    }

    @Test
    void removeUserFromParkingLot_Success() throws MessagingException {
        ParkingLotUserId id = new ParkingLotUserId(1L, 100L);
        ParkingLotUser parkingLotUser = new ParkingLotUser();
        parkingLotUser.setId(id);
        parkingLotUser.setUser(testUser);
        parkingLotUser.setParkingLot(testParkingLot);

        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(parkingLotRepository.findById(100L)).thenReturn(Optional.of(testParkingLot));
        when(parkingLotUserRepository.findById(id)).thenReturn(Optional.of(parkingLotUser));

        ParkingLotResponseDto response = parkingLotService.removeUserFromParkingLot(1L, 100L);

        assertTrue(response.success());
        assertEquals(Collections.emptySet(), response.error());

        verify(parkingLotUserRepository).delete(parkingLotUser);
        verify(emailSenderService).sendEmail("test@example.com", "You have been removed from Parking Lot", "You have been removed from Test Parking Lot");
    }

    @Test
    void removeUserFromParkingLot_UserNotFound() {
        when(userRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> parkingLotService.removeUserFromParkingLot(2L, 100L));
        verifyNoInteractions(emailSenderService);
    }

    @Test
    void removeUserFromParkingLot_ParkingLotNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(parkingLotRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(ParkingLotException.class, () -> parkingLotService.removeUserFromParkingLot(1L, 999L));
        verifyNoInteractions(emailSenderService);
    }

    @Test
    void removeUserFromParkingLot_UserNotAssigned() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(parkingLotRepository.findById(100L)).thenReturn(Optional.of(testParkingLot));
        when(parkingLotUserRepository.findById(new ParkingLotUserId(1L, 100L))).thenReturn(Optional.empty());

        assertThrows(UserNotAssignedException.class, () -> parkingLotService.removeUserFromParkingLot(1L, 100L));
        verifyNoInteractions(emailSenderService);
    }

    @Test
    void removeUserFromParkingLot_EmailSendError() throws MessagingException {
        ParkingLotUserId id = new ParkingLotUserId(1L, 100L);
        ParkingLotUser parkingLotUser = new ParkingLotUser();
        parkingLotUser.setId(id);
        parkingLotUser.setUser(testUser);
        parkingLotUser.setParkingLot(testParkingLot);

        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(parkingLotRepository.findById(100L)).thenReturn(Optional.of(testParkingLot));
        when(parkingLotUserRepository.findById(id)).thenReturn(Optional.of(parkingLotUser));
        doThrow(new MessagingException("Send failed")).when(emailSenderService)
                .sendEmail(anyString(), anyString(), anyString());

        assertThrows(EmailSendException.class, () -> parkingLotService.removeUserFromParkingLot(1L, 100L));
    }
}