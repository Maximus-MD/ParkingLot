package com.endava.md.internship.parkinglot.service.impl;

import com.endava.md.internship.parkinglot.dto.ParkingLotResponseDto;
import com.endava.md.internship.parkinglot.dto.ParkingSpotDto;
import com.endava.md.internship.parkinglot.exception.CustomAuthException;
import com.endava.md.internship.parkinglot.exception.OccupiedParkingSpotException;
import com.endava.md.internship.parkinglot.exception.ParkingSpotClosedException;
import com.endava.md.internship.parkinglot.exception.ParkingSpotNotFoundException;
import com.endava.md.internship.parkinglot.exception.UserAlreadyHasParkingSpotException;
import com.endava.md.internship.parkinglot.exception.UserNotAssignedException;
import com.endava.md.internship.parkinglot.model.ParkingLotUserId;
import com.endava.md.internship.parkinglot.model.ParkingSpot;
import com.endava.md.internship.parkinglot.model.Role;
import com.endava.md.internship.parkinglot.model.User;
import com.endava.md.internship.parkinglot.repository.ParkingLotUserRepository;
import com.endava.md.internship.parkinglot.repository.ParkingSpotRepository;
import com.endava.md.internship.parkinglot.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.endava.md.internship.parkinglot.model.ParkingSpotType.TEMP_CLOSED;
import static com.endava.md.internship.parkinglot.utils.ParkingLotDTOUtils.getPreparedParkingSpotRequestDto;
import static com.endava.md.internship.parkinglot.utils.ParkingLotUtils.getOneParkingSpot;
import static com.endava.md.internship.parkinglot.utils.ParkingLotUtils.getParkingLotUser;
import static com.endava.md.internship.parkinglot.utils.ParkingLotUtils.getParkingLotUserId;
import static com.endava.md.internship.parkinglot.utils.RoleUtils.getPreparedAdminRole;
import static com.endava.md.internship.parkinglot.utils.UserUtils.getPreparedUser;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ParkingSpotServiceTest {

    @Mock
    private ParkingLotUserRepository parkingLotUserRepository;

    @Mock
    private ParkingSpotRepository parkingSpotRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ParkingSpotServiceImpl parkingSpotService;

    @Test
    void testOccupyParkingSpot_WhenSpotIsNotOccupied() {
        ParkingSpotDto parkingSpotDto = getPreparedParkingSpotRequestDto();

        when(userRepository.findByEmailIgnoreCase("Alex@gmail.com"))
                .thenReturn(Optional.of(getPreparedUser()));

        ParkingSpot parkingSpot = getOneParkingSpot();

        ParkingLotUserId parkingLotUserId = getParkingLotUserId();

        getParkingLotUser();

        when(parkingSpotRepository.findByNameAndParkingName(any(), any()))
                .thenReturn(Optional.of(parkingSpot));
        when(parkingLotUserRepository.existsById_UserId(parkingLotUserId.getUserId()))
                .thenReturn(true);
        when(parkingSpotRepository.save(parkingSpot)).thenReturn(parkingSpot);

        ParkingLotResponseDto response = parkingSpotService.occupyParkingSpot(parkingSpotDto);

        assertNotNull(response);
        assertTrue(response.success());

        verify(parkingSpotRepository).save(any(ParkingSpot.class));
        verify(userRepository).findByEmailIgnoreCase("Alex@gmail.com");
        verify(parkingLotUserRepository).existsById_UserId(parkingLotUserId.getUserId());
    }

    @Test
    void testOccupyParkingSpot_WhenSpotIsOccupied_ThrowsParkingSpotOccupiedException() {
        ParkingSpotDto parkingSpotDto = getPreparedParkingSpotRequestDto();

        when(userRepository.findByEmailIgnoreCase("Alex@gmail.com"))
                .thenReturn(Optional.of(getPreparedUser()));

        ParkingSpot parkingSpot = getOneParkingSpot();
        parkingSpot.setOccupied(true);

        ParkingLotUserId parkingLotUserId = getParkingLotUserId();

        getParkingLotUser();

        when(parkingSpotRepository.findByNameAndParkingName(any(), any()))
                .thenReturn(Optional.of(parkingSpot));
        when(parkingLotUserRepository.existsById_UserId(parkingLotUserId.getUserId()))
                .thenReturn(true);

        OccupiedParkingSpotException exception = assertThrows(OccupiedParkingSpotException.class,
                () -> parkingSpotService.occupyParkingSpot(parkingSpotDto));

        assertEquals("Parking spot A-001 is already occupied.", exception.getMessage());

        verify(parkingSpotRepository).findByNameAndParkingName(any(), any());
        verify(userRepository).findByEmailIgnoreCase(any());
        verify(parkingLotUserRepository).existsById_UserId(any());
    }

    @Test
    void testOccupyParkingSpot_WhenParkingSpotDoesntExist_ThrowsParkingSpotNotFoundException() {
        ParkingSpotDto parkingSpotDto = getPreparedParkingSpotRequestDto();

        ParkingSpot parkingSpot = getOneParkingSpot();
        parkingSpot.setName(null);

        ParkingSpotNotFoundException exception = assertThrows(ParkingSpotNotFoundException.class, () ->
                parkingSpotService.occupyParkingSpot(parkingSpotDto));

        assertEquals("Parking spot A-001 not found in parking lot Kaufland.", exception.getMessage());
    }

    @Test
    void testOccupyParkingSpot_WhenUserAlreadyOccupiedParkingSpot_ThrowsUserAlreadyHasParkingSpotException() {
        ParkingSpotDto parkingSpotDto = getPreparedParkingSpotRequestDto();

        User user = getPreparedUser();

        ParkingSpot parkingSpot1 = getOneParkingSpot();
        parkingSpot1.setOccupied(true);
        parkingSpot1.setUser(user);

        ParkingSpot parkingSpot2 = getOneParkingSpot();
        parkingSpot2.setSpotId(2L);
        parkingSpot2.setName("A-002");

        ParkingLotUserId parkingLotUserId = getParkingLotUserId();

        getParkingLotUser();

        when(parkingSpotRepository.findByNameAndParkingName(anyString(), anyString()))
                .thenReturn(Optional.of(parkingSpot2));
        when(parkingLotUserRepository.existsById_UserId(parkingLotUserId.getUserId()))
                .thenReturn(true);
        when(userRepository.findByEmailIgnoreCase(parkingSpotDto.email()))
                .thenReturn(Optional.of(user));
        when(parkingSpotRepository.findByUser_UserId(user.getUserId()))
                .thenReturn(Optional.of(parkingSpot1));

        UserAlreadyHasParkingSpotException exception = assertThrows(UserAlreadyHasParkingSpotException.class,
                () -> parkingSpotService.occupyParkingSpot(parkingSpotDto));

        assertEquals("User Alex@gmail.com has already A-001 parking spot.", exception.getMessage());

        verify(parkingSpotRepository).findByNameAndParkingName(any(), any());
        verify(userRepository).findByEmailIgnoreCase(any());
        verify(parkingSpotRepository).findByUser_UserId(any());
        verify(parkingLotUserRepository).existsById_UserId(any());
    }

    @Test
    void testOccupyParkingSpot_WhenParkingSpotIsOccupied_ThrowsParkingSpotClosedException() {
        ParkingSpotDto parkingSpotDto = getPreparedParkingSpotRequestDto();

        when(userRepository.findByEmailIgnoreCase("Alex@gmail.com"))
                .thenReturn(Optional.of(getPreparedUser()));

        ParkingSpot parkingSpot = getOneParkingSpot();
        parkingSpot.setType(TEMP_CLOSED);

        ParkingLotUserId parkingLotUserId = getParkingLotUserId();

        getParkingLotUser();

        when(parkingSpotRepository.findByNameAndParkingName(anyString(), anyString()))
                .thenReturn(Optional.of(parkingSpot));
        when(parkingLotUserRepository.existsById_UserId(parkingLotUserId.getUserId()))
                .thenReturn(true);

        ParkingSpotClosedException exception = assertThrows(ParkingSpotClosedException.class, () ->
                parkingSpotService.occupyParkingSpot(parkingSpotDto));

        assertEquals("Parking spot A-001 is temporary closed.", exception.getMessage());

        verify(parkingSpotRepository).findByNameAndParkingName(any(), any());
        verify(userRepository).findByEmailIgnoreCase(any());
        verify(parkingSpotRepository).findByUser_UserId(any());
        verify(parkingLotUserRepository).existsById_UserId(any());
    }

    @Test
    void testOccupyParkingSpot_WhenUserIsAdmin_ThrowsCustomAuthException() {
        ParkingSpotDto parkingSpotDto = getPreparedParkingSpotRequestDto();

        Role role = getPreparedAdminRole();

        User user = getPreparedUser();
        user.setRole(role);

        when(userRepository.findByEmailIgnoreCase(user.getEmail()))
                .thenReturn(Optional.of(user));

        ParkingSpot parkingSpot = getOneParkingSpot();

        ParkingLotUserId parkingLotUserId = getParkingLotUserId();

        getParkingLotUser();

        when(parkingSpotRepository.findByNameAndParkingName(anyString(), anyString()))
                .thenReturn(Optional.of(parkingSpot));
        when(parkingLotUserRepository.existsById_UserId(parkingLotUserId.getUserId()))
                .thenReturn(true);

        CustomAuthException exception = assertThrows(CustomAuthException.class, () ->
                parkingSpotService.occupyParkingSpot(parkingSpotDto));

        assertEquals("User Alex@gmail.com has no REGULAR permissions.", exception.getMessage());

        verify(parkingSpotRepository).findByNameAndParkingName(any(), any());
        verify(userRepository).findByEmailIgnoreCase(any());
        verify(parkingSpotRepository).findByUser_UserId(any());
        verify(parkingLotUserRepository).existsById_UserId(any());
    }

    @Test
    void testOccupyParkingSpot_WhenUserNotAssignToParkingLot_ThrowsUserNotAssignedException() {
        ParkingSpotDto parkingSpotDto = getPreparedParkingSpotRequestDto();

        User user = getPreparedUser();

        when(userRepository.findByEmailIgnoreCase(user.getEmail()))
                .thenReturn(Optional.of(user));

        ParkingSpot parkingSpot = getOneParkingSpot();

        getParkingLotUserId();

        getParkingLotUser();

        when(parkingSpotRepository.findByNameAndParkingName(anyString(), anyString()))
                .thenReturn(Optional.of(parkingSpot));
        when(parkingLotUserRepository.existsById_UserId(any()))
                .thenReturn(false);

        UserNotAssignedException exception = assertThrows(UserNotAssignedException.class, () ->
                parkingSpotService.occupyParkingSpot(parkingSpotDto));

        assertEquals("Alex@gmail.com not found in Kaufland parking lot.", exception.getMessage());

        verify(parkingSpotRepository).findByNameAndParkingName(any(), any());
        verify(userRepository).findByEmailIgnoreCase(any());
        verify(parkingLotUserRepository).existsById_UserId(any());
    }
}