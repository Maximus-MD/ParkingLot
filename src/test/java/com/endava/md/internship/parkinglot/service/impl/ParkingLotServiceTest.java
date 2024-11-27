package com.endava.md.internship.parkinglot.service.impl;

import com.endava.md.internship.parkinglot.dto.ParkingLotRequestDto;
import com.endava.md.internship.parkinglot.dto.ParkingLotResponseDto;
import com.endava.md.internship.parkinglot.exception.ParkingLotException;
import com.endava.md.internship.parkinglot.model.ParkingLevel;
import com.endava.md.internship.parkinglot.model.ParkingLot;
import com.endava.md.internship.parkinglot.model.WorkingDay;
import com.endava.md.internship.parkinglot.repository.ParkingLevelRepository;
import com.endava.md.internship.parkinglot.repository.ParkingLotRepository;
import com.endava.md.internship.parkinglot.repository.WorkingDayRepository;
import com.endava.md.internship.parkinglot.utils.ParkingLotDTOUtils;
import com.endava.md.internship.parkinglot.utils.ParkingLotUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.endava.md.internship.parkinglot.utils.ParkingLotDTOUtils.getPreparedParkingLotRequestDtoNullTime;
import static com.endava.md.internship.parkinglot.utils.ParkingLotDTOUtils.getPreparedParkingLotRequestDtoWithInvalidDay;
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

@ExtendWith(MockitoExtension.class)
class ParkingLotServiceTest {

    @Mock
    private ParkingLotRepository parkingLotRepository;

    @Mock
    private ParkingLevelRepository parkingLevelRepository;

    @Mock
    private WorkingDayRepository workingDayRepository;

    @InjectMocks
    private ParkingLotServiceImpl parkingLotService;

    @Test
    void testParkingLotCreationWhenAvailable() {

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
    void testWhenOperatesNonStopEqualsTrue(){
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
}
