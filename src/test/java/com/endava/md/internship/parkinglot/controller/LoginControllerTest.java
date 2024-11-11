package com.endava.md.internship.parkinglot.controller;

import com.endava.md.internship.parkinglot.dto.LoginRequestDto;
import com.endava.md.internship.parkinglot.dto.LoginResponseDto;
import com.endava.md.internship.parkinglot.service.AuthService;
import com.endava.md.internship.parkinglot.utils.LoginDTOUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ExtendWith(MockitoExtension.class)
public class LoginControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AuthService authService;

    @InjectMocks
    private LoginController loginController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(loginController).build();
    }

    @Test
    void loginTest_ReturnsResponseEntityLoginResponseDTO() throws Exception {
        LoginRequestDto loginRequestDto = LoginDTOUtils.getPreparedRequestDto();
        LoginResponseDto loginResponseDto = LoginDTOUtils.getPreparedResponseDto();

        when(authService.login(any(LoginRequestDto.class))).thenReturn(loginResponseDto);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequestDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.token").value(loginResponseDto.token()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error").value((Object) null))
                .andDo(print());
    }
}