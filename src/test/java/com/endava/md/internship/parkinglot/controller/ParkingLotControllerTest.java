package com.endava.md.internship.parkinglot.controller;

import com.endava.md.internship.parkinglot.dto.ParkingLotGeneralDetailsDto;
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
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(controllers = ParkingLotController.class)
@AutoConfigureMockMvc(addFilters = false)
class ParkingLotControllerTest {

    @MockitoBean
    private ParkingLotService parkingLotService;

    @MockitoBean
    private ParkingLotRepository parkingLotRepository;

    @MockitoBean
    private UniqueParkingNameValidator uniqueParkingNameValidator;

    @MockitoBean
    private UniqueParkingAddressValidator uniqueParkingAddressValidator;

    @MockitoBean
    private JWTService jwtService;

    @MockitoBean
    private JWTUtils jwtUtils;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    private static final int PARKING_NOT_FOUND_CODE = 1013;
    private static final int USER_NOT_ASSIGNED_CODE = 1030;
    private static final int USER_ALREADY_ASSIGNED_CODE = 1031;
    private static final int USER_NOT_FOUND_CODE = 4033;

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

    @Test
    void shouldReturnOkStatusWithResponse_WhenParkingLotDeleteIsSuccessful() throws Exception {
        ParkingLotRequestDto requestDto = ParkingLotDTOUtils.getPreparedParkingLotRequestDto();
        ParkingLotResponseDto responseDto = ParkingLotDTOUtils.getPreparedParkingLotResponseDto();

        when(parkingLotService.deleteParkingLot(any())).thenReturn(responseDto);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/parking-lots/delete/Kaufland")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error").isEmpty());
    }

    private ParkingLotGeneralDetailsDto createParkingLotDto(
            long id,
            String name,
            String operatingHours,
            List<String> operatingDays,
            boolean isTemporaryClosed,
            boolean operatesNonStop,
            int totalSpots,
            int availableSpots,
            double loadPercentage
    ) {
        return new ParkingLotGeneralDetailsDto(id, name, operatingHours, operatingDays, isTemporaryClosed, operatesNonStop, totalSpots, availableSpots, loadPercentage);
    }

    @WithMockUser
    @Test
    void getAllParkingLots_ReturnsListOfParkingLotDTOs() throws Exception {
        List<ParkingLotGeneralDetailsDto> parkingLots = List.of(
                createParkingLotDto(1L, "Endava Tower Parking Lot", "08:00 - 18:00",  Arrays.asList("MONDAY", "TUESDAY"), false, false, 100, 90, 10.0),
                createParkingLotDto(2L, "Kaufland Parking Lot", "", Collections.emptyList(), false, true, 200, 200, 0.0),
                createParkingLotDto(3L, "N1 Hypermarket Parking Lot", "", Collections.emptyList(), true, false, 50, 10, 80.0)
        );

        when(parkingLotService.getAllParkingLots()).thenReturn(parkingLots);

        mockMvc.perform(get("/parking-lots"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].parkingLotId").value(1L))
                .andExpect(jsonPath("$[0].name").value("Endava Tower Parking Lot"))
                .andExpect(jsonPath("$[0].operatingHours").value("08:00 - 18:00"))
                .andExpect(jsonPath("$[0].operatingDays[0]").value("MONDAY"))
                .andExpect(jsonPath("$[0].operatingDays[1]").value("TUESDAY"))
                .andExpect(jsonPath("$[0].isTemporaryClosed").value(false))
                .andExpect(jsonPath("$[0].operatesNonStop").value(false))
                .andExpect(jsonPath("$[0].totalSpots").value(100))
                .andExpect(jsonPath("$[0].availableSpots").value(90))
                .andExpect(jsonPath("$[0].loadPercentage").value(10.0))

                .andExpect(jsonPath("$[1].parkingLotId").value(2L))
                .andExpect(jsonPath("$[1].name").value("Kaufland Parking Lot"))
                .andExpect(jsonPath("$[1].isTemporaryClosed").value(false))
                .andExpect(jsonPath("$[1].operatesNonStop").value(true))
                .andExpect(jsonPath("$[1].totalSpots").value(200))
                .andExpect(jsonPath("$[1].availableSpots").value(200))
                .andExpect(jsonPath("$[1].loadPercentage").value(0.0))

                .andExpect(jsonPath("$[2].parkingLotId").value(3L))
                .andExpect(jsonPath("$[2].name").value("N1 Hypermarket Parking Lot"))
                .andExpect(jsonPath("$[2].isTemporaryClosed").value(true))
                .andExpect(jsonPath("$[2].operatesNonStop").value(false))
                .andExpect(jsonPath("$[2].totalSpots").value(50))
                .andExpect(jsonPath("$[2].availableSpots").value(10))
                .andExpect(jsonPath("$[2].loadPercentage").value(80.0));
    }

    @WithMockUser
    @Test
    void getAllParkingLots_ReturnsEmptyList_WhenNoParkingLotsAvailable() throws Exception {
        when(parkingLotService.getAllParkingLots()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/parking-lots"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    private ParkingLotResponseDto successResponse() {
        return ParkingLotResponseDto.builder()
                .success(true)
                .error(Collections.emptySet())
                .build();
    }

    private ParkingLotResponseDto errorResponse(int code) {
        return ParkingLotResponseDto.builder()
                .success(false)
                .error(Set.of(code))
                .build();
    }

    @Test
    void getAllParkingLots_EmptyList() throws Exception {
        when(parkingLotService.getAllParkingLots()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/parking-lots"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void removeParkingLot_Success() throws Exception {
        when(parkingLotService.deleteParkingLot("MyParking")).thenReturn(successResponse());

        mockMvc.perform(delete("/parking-lots/delete/MyParking"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.error").isEmpty());
    }

    @Test
    void removeParkingLot_Error() throws Exception {
        when(parkingLotService.deleteParkingLot("NonExistent"))
                .thenReturn(errorResponse(PARKING_NOT_FOUND_CODE));

        mockMvc.perform(delete("/parking-lots/delete/NonExistent"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.error[0]").value(PARKING_NOT_FOUND_CODE));
    }

    @Test
    void addUserToParkingLot_Success() throws Exception {
        when(parkingLotService.addUserToParkingLot(100L, 10L)).thenReturn(successResponse());

        mockMvc.perform(post("/parking-lots/10/users/100"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.error").isEmpty());
    }

    @Test
    void addUserToParkingLot_UserNotFound() throws Exception {
        when(parkingLotService.addUserToParkingLot(200L, 20L))
                .thenReturn(errorResponse(USER_NOT_FOUND_CODE));

        mockMvc.perform(post("/parking-lots/20/users/200"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.error[0]").value(USER_NOT_FOUND_CODE));
    }

    @Test
    void addUserToParkingLot_UserAlreadyAssigned() throws Exception {
        when(parkingLotService.addUserToParkingLot(300L, 30L))
                .thenReturn(errorResponse(USER_ALREADY_ASSIGNED_CODE));

        mockMvc.perform(post("/parking-lots/30/users/300"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.error[0]").value(USER_ALREADY_ASSIGNED_CODE));
    }

    @Test
    void removeUserFromParkingLot_Success() throws Exception {
        when(parkingLotService.removeUserFromParkingLot(400L, 40L)).thenReturn(successResponse());

        mockMvc.perform(delete("/parking-lots/40/users/400"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.error").isEmpty());
    }

    @Test
    void removeUserFromParkingLot_UserNotAssigned() throws Exception {
        when(parkingLotService.removeUserFromParkingLot(500L, 50L))
                .thenReturn(errorResponse(USER_NOT_ASSIGNED_CODE));

        mockMvc.perform(delete("/parking-lots/50/users/500"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.error[0]").value(USER_NOT_ASSIGNED_CODE));
    }
}