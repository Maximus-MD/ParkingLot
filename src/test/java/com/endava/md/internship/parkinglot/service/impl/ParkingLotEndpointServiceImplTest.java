package com.endava.md.internship.parkinglot.service.impl;

import com.endava.md.internship.parkinglot.dto.ParkingLotGeneralDetailsDto;
import com.endava.md.internship.parkinglot.model.ParkingLot;
import com.endava.md.internship.parkinglot.model.WorkingDay;
import com.endava.md.internship.parkinglot.repository.ParkingLotRepository;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ParkingLotEndpointServiceImplTest {

    @Mock
    private ParkingLotRepository parkingLotRepository;

    @InjectMocks
    private ParkingLotServiceImpl parkingLotService;

    private ParkingLotGeneralDetailsDto createParkingLotListDto(Long id, String name, String workingHours, String workingDays, boolean isTemporaryClosed, boolean operatesNonStop) {
        return new ParkingLotGeneralDetailsDto(id, name, workingHours, workingDays, isTemporaryClosed, operatesNonStop);
    }

    @Test
    void getAllParkingLots_ReturnsEmptyList_whenNoParkingLotsInDatabase() {
        when(parkingLotRepository.findAll()).thenReturn(new ArrayList<>());

        List<ParkingLotGeneralDetailsDto> result = parkingLotService.getAllParkingLots();

        assertNotNull(result, "Result should not be null");
        assertTrue(result.isEmpty(), "The result list should be empty");
    }

    @Test
    void getAllParkingLots_ReturnsParkingLotDetails_whenParkingLotIsOperational() {
        ParkingLot lot = new ParkingLot();
        lot.setParkingLotId(1L);
        lot.setName("Endava Tower Parking Lot");
        lot.setStartTime(Time.valueOf("08:00:00"));
        lot.setEndTime(Time.valueOf("18:00:00"));
        lot.setWorkingDays(Arrays.asList(
                createWorkingDay(DayOfWeek.MONDAY),
                createWorkingDay(DayOfWeek.TUESDAY)
        ));
        lot.setOperatesNonStop(false);
        lot.setTemporaryClosed(false);

        when(parkingLotRepository.findAll()).thenReturn(Collections.singletonList(lot));

        List<ParkingLotGeneralDetailsDto> result = parkingLotService.getAllParkingLots();

        assertNotNull(result);
        assertEquals(1, result.size());

        ParkingLotGeneralDetailsDto expectedDto = createParkingLotListDto(1L, "Endava Tower Parking Lot", "08:00 - 18:00", "MONDAY/TUESDAY", false, false);
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

        ParkingLotGeneralDetailsDto expectedDto = createParkingLotListDto(2L, "Kaufland Parking Lot", "", "", false, true);
        assertEquals(expectedDto, result.get(0));
    }

    @Test
    void getAll_whenParkingLots_ReturnsIsTemporarilyClosed_WhenParkingLotIsTemporaryClosed () {
        ParkingLot lot = new ParkingLot();
        lot.setParkingLotId(3L);
        lot.setName("N1 Hypermarket Parking Lot");
        lot.setTemporaryClosed(true);

        when(parkingLotRepository.findAll()).thenReturn(Collections.singletonList(lot));

        List<ParkingLotGeneralDetailsDto> result = parkingLotService.getAllParkingLots();

        assertNotNull(result);
        assertEquals(1, result.size());

        ParkingLotGeneralDetailsDto expectedDto = createParkingLotListDto(3L, "N1 Hypermarket Parking Lot", "", "", true, false);
        assertEquals(expectedDto, result.get(0));
    }

    private WorkingDay createWorkingDay(DayOfWeek dayOfWeek) {
        WorkingDay workingDay = new WorkingDay();
        workingDay.setDayName(dayOfWeek);
        return workingDay;
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

        ParkingLotGeneralDetailsDto expectedDto = createParkingLotListDto(4L, "Empty Days Parking Lot", "08:00 - 18:00", "", false, false);
        assertEquals(expectedDto, result.get(0));
    }
}