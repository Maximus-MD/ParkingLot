package com.endava.md.internship.parkinglot.controller;

import com.endava.md.internship.parkinglot.dto.ParkingLotRequestDto;
import com.endava.md.internship.parkinglot.dto.ParkingLotResponseDto;
import com.endava.md.internship.parkinglot.repository.ParkingLotRepository;
import com.endava.md.internship.parkinglot.security.JWTService;
import com.endava.md.internship.parkinglot.security.JWTUtils;
import com.endava.md.internship.parkinglot.service.ParkingLotService;
import com.endava.md.internship.parkinglot.utils.ParkingLotDTOUtils;
import com.endava.md.internship.parkinglot.validation.validator.UniqueParkingAddressValidator;
import com.endava.md.internship.parkinglot.validation.validator.UniqueParkingNameValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ParkingLotController.class)
@AutoConfigureMockMvc(addFilters = false)
class ParkingLotControllerTest {

    @MockBean
    private ParkingLotService parkingLotService;

    @MockBean
    private ParkingLotRepository parkingLotRepository;

    @MockBean
    private UniqueParkingNameValidator uniqueParkingNameValidator;

    @MockBean
    private UniqueParkingAddressValidator uniqueParkingAddressValidator;

    @MockBean
    private JWTService jwtService;

    @MockBean
    private JWTUtils jwtUtils;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;


    @Test
    void shouldReturnOkStatusWithResponse_WhenParkingLotCreationIsSuccessful() throws Exception {
        ParkingLotRequestDto requestDto = ParkingLotDTOUtils.getPreparedParkingLotRequestDto();
        ParkingLotResponseDto responseDto = ParkingLotDTOUtils.getPreparedParkingLotResponseDto();

        when(parkingLotService.createParkingLot(any())).thenReturn(responseDto);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/parking-lots/create")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error").isEmpty());
    }
}
