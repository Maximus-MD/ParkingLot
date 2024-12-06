package com.endava.md.internship.parkinglot.service.impl;

import com.endava.md.internship.parkinglot.dto.ParkingLotResponseDto;
import com.endava.md.internship.parkinglot.dto.ParkingSpotDto;
import com.endava.md.internship.parkinglot.exception.ParkingSpotNotFoundException;
import com.endava.md.internship.parkinglot.exception.ParkingSpotOccupiedException;
import com.endava.md.internship.parkinglot.exception.UserAlreadyHasParkingSpotException;
import com.endava.md.internship.parkinglot.model.ParkingSpot;
import com.endava.md.internship.parkinglot.model.User;
import com.endava.md.internship.parkinglot.repository.ParkingSpotRepository;
import com.endava.md.internship.parkinglot.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.endava.md.internship.parkinglot.utils.ParkingLotDTOUtils.getPreparedParkingSpotRequestDto;
import static com.endava.md.internship.parkinglot.utils.ParkingLotUtils.getOneParkingSpot;
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

        when(parkingSpotRepository.findByNameAndParkingName(any(), any())).thenReturn(Optional.of(parkingSpot));
        when(parkingSpotRepository.save(parkingSpot)).thenReturn(parkingSpot);

        ParkingLotResponseDto response = parkingSpotService.occupyParkingSpot(parkingSpotDto);

        assertNotNull(response);
        assertTrue(response.success());

        verify(parkingSpotRepository).save(any(ParkingSpot.class));
        verify(userRepository).findByEmailIgnoreCase("Alex@gmail.com");
    }

    @Test
    void testOccupyParkingSpot_WhenSpotIsOccupied_ThrowsParkingSpotOccupiedException() {
        ParkingSpotDto parkingSpotDto = getPreparedParkingSpotRequestDto();

        when(userRepository.findByEmailIgnoreCase("Alex@gmail.com"))
                .thenReturn(Optional.of(getPreparedUser()));

        ParkingSpot parkingSpot = getOneParkingSpot();
        parkingSpot.setOccupied(true);

        when(parkingSpotRepository.findByNameAndParkingName(any(), any()))
                .thenReturn(Optional.of(parkingSpot));

        ParkingSpotOccupiedException exception = assertThrows(ParkingSpotOccupiedException.class,
                () -> parkingSpotService.occupyParkingSpot(parkingSpotDto));

        assertEquals("Parking spot A-001 is already occupied.", exception.getMessage());

        verify(parkingSpotRepository).findByNameAndParkingName(any(), any());
        verify(userRepository).findByEmailIgnoreCase(any());
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

        when(parkingSpotRepository.findByNameAndParkingName(anyString(), anyString()))
                .thenReturn(Optional.of(parkingSpot2));
        when(userRepository.findByEmailIgnoreCase(parkingSpotDto.email())).thenReturn(Optional.of(user));
        when(parkingSpotRepository.findByUser_UserId(user.getUserId())).thenReturn(Optional.of(parkingSpot1));

        UserAlreadyHasParkingSpotException exception = assertThrows(UserAlreadyHasParkingSpotException.class,
                () -> parkingSpotService.occupyParkingSpot(parkingSpotDto));

        assertEquals("User Alex@gmail.com has already A-001 parking spot.", exception.getMessage());

        verify(parkingSpotRepository).findByNameAndParkingName(any(), any());
        verify(userRepository).findByEmailIgnoreCase(any());
        verify(parkingSpotRepository).findByUser_UserId(any());
    }
}
