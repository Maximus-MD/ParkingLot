package com.endava.md.internship.parkinglot.controller;

import com.endava.md.internship.parkinglot.dto.ParkingSpotResponseDto;
import com.endava.md.internship.parkinglot.dto.ParkingSpotTypeDto;
import com.endava.md.internship.parkinglot.model.ParkingSpotType;
import com.endava.md.internship.parkinglot.security.JWTService;
import com.endava.md.internship.parkinglot.security.JWTUtils;
import com.endava.md.internship.parkinglot.service.ParkingSpotService;
import com.endava.md.internship.parkinglot.utils.ParkingSpotResponseDTOUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = ParkingSpotController.class)
@AutoConfigureMockMvc(addFilters = false)
class ParkingSpotControllerTest {

    @MockitoBean
    private ParkingSpotService parkingSpotService;

    @MockitoBean
    private JWTService jwtService;

    @MockitoBean
    private JWTUtils jwtUtils;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void should_ReturnParkingSpotWithChangedType_When_RequestContainsNewCorrectParkingSpotType() throws Exception {
        ParkingSpotResponseDto expectedParkingSpotResponseDto =
                ParkingSpotResponseDTOUtils.getPreparedParkingSpotResponseDtoWithHandicapType();
        String handicapParkingSpotType = "HANDICAP";
        ParkingSpotTypeDto parkingSpotTypeDto = new ParkingSpotTypeDto(handicapParkingSpotType);
        Long parkingSpotId = 10L;

        when(parkingSpotService.changeParkingSpotType(parkingSpotId, parkingSpotTypeDto))
                .thenReturn(expectedParkingSpotResponseDto);

        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/parking-spots/change-type/{id}", parkingSpotId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(parkingSpotTypeDto)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedParkingSpotResponseDto)));
    }

    @Test
    void should_ReturnParkingSpotTypesList_When_GetParkingSpotTypesMethodWasCalled() throws Exception {
        List<String> parkingSpotTypesList = Arrays.stream(ParkingSpotType.values()).map(Enum::name).toList();

        when(parkingSpotService.getAllParkingSpotTypes()).thenReturn(parkingSpotTypesList);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/parking-spots/types"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(parkingSpotTypesList)));

        verify(parkingSpotService, times(1)).getAllParkingSpotTypes();
    }

}