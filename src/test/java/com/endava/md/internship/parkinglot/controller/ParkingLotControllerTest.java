package com.endava.md.internship.parkinglot.controller;

import com.endava.md.internship.parkinglot.dto.ParkingLotRequestDto;
import com.endava.md.internship.parkinglot.dto.ParkingLotResponseDto;
import com.endava.md.internship.parkinglot.service.ParkingLotService;
import com.endava.md.internship.parkinglot.utils.ParkingLotDTOUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@ExtendWith(MockitoExtension.class)
class ParkingLotControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ParkingLotService parkingLotService;

    @InjectMocks
    private ParkingLotController parkingLotController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(parkingLotController).build();
    }

    @Test
    void shouldReturnOkStatusWithResponse_WhenParkingLotCreationIsSuccessful() throws Exception {
        ParkingLotRequestDto requestDto = ParkingLotDTOUtils.getPreparedParkingLotRequestDto();
        ParkingLotResponseDto responseDto = ParkingLotDTOUtils.getPreparedParkingLotResponseDto();

        when(parkingLotService.createParkingLot(any())).thenReturn(responseDto);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/parking-lots/create")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error").isEmpty());
    }
}
