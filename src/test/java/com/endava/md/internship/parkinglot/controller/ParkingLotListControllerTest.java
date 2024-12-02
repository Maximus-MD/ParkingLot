package com.endava.md.internship.parkinglot.controller;

import java.util.Arrays;
import java.util.List;

import com.endava.md.internship.parkinglot.dto.ParkingLotGeneralDetailsDto;
import com.endava.md.internship.parkinglot.security.JWTService;
import com.endava.md.internship.parkinglot.security.JWTUtils;

import com.endava.md.internship.parkinglot.service.ParkingLotService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.security.test.context.support.WithMockUser;

@WebMvcTest(ParkingLotController.class)
class ParkingLotListControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ParkingLotService parkingService;

    @MockBean
    private JWTService jwtService;

    @MockBean
    private JWTUtils jwtUtils;

    private ParkingLotGeneralDetailsDto createParkingLotDto(
            long id,
            String name,
            String operatingHours,
            String operatingDays,
            boolean isTemporaryClosed,
            boolean operatesNonStop
    ) {
        return new ParkingLotGeneralDetailsDto(id, name, operatingHours, operatingDays, isTemporaryClosed, operatesNonStop);
    }

    @WithMockUser
    @Test
    void getAllParkingLots_ReturnsListOfParkingLotDTOs() throws Exception {
        List<ParkingLotGeneralDetailsDto> parkingLots = List.of(
                createParkingLotDto(1L, "Endava Tower Parking Lot", "08:00 - 18:00", "Monday, Tuesday", false, false),
                createParkingLotDto(2L, "Kaufland Parking Lot", "", "", false, true),
                createParkingLotDto(3L, "N1 Hypermarket Parking Lot", "", "", true, false)
        );

        when(parkingService.getAllParkingLots()).thenReturn(parkingLots);

        mockMvc.perform(get("/parking-lots"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].parkingLotId").value(1L))
                .andExpect(jsonPath("$[0].name").value("Endava Tower Parking Lot"))
                .andExpect(jsonPath("$[0].operatingHours").value("08:00 - 18:00"))
                .andExpect(jsonPath("$[0].operatingDays").value("Monday, Tuesday"))
                .andExpect(jsonPath("$[0].isTemporaryClosed").value(false))
                .andExpect(jsonPath("$[0].operatesNonStop").value(false))
                .andExpect(jsonPath("$[1].parkingLotId").value(2L))
                .andExpect(jsonPath("$[1].name").value("Kaufland Parking Lot"))
                .andExpect(jsonPath("$[1].isTemporaryClosed").value(false))
                .andExpect(jsonPath("$[1].operatesNonStop").value(true))
                .andExpect(jsonPath("$[2].parkingLotId").value(3L))
                .andExpect(jsonPath("$[2].name").value("N1 Hypermarket Parking Lot"))
                .andExpect(jsonPath("$[2].isTemporaryClosed").value(true))
                .andExpect(jsonPath("$[2].operatesNonStop").value(false));
    }

    @WithMockUser
    @Test
    void getAllParkingLots_ReturnsEmptyList_WhenNoParkingLotsAvailable() throws Exception {
        when(parkingService.getAllParkingLots()).thenReturn(Arrays.asList());

        mockMvc.perform(get("/parking-lots"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)));
    }
}