package com.endava.md.internship.parkinglot.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.endava.md.internship.parkinglot.dto.RegistrationRequestDto;
import com.endava.md.internship.parkinglot.exception.RegistrationException;
import com.endava.md.internship.parkinglot.repository.UserRepository;
import com.endava.md.internship.parkinglot.security.JWTService;
import com.endava.md.internship.parkinglot.security.JWTUtils;
import com.endava.md.internship.parkinglot.service.UserService;
import com.endava.md.internship.parkinglot.utils.RegistrationDTOUtils;
import com.endava.md.internship.parkinglot.utils.TokenUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = RegistrationController.class)
@AutoConfigureMockMvc(addFilters = false)
class RegistrationControllerTest {

    @MockBean
    private UserService userService;

    @MockBean
    private JWTService jwtService;

    @MockBean
    private JWTUtils jwtUtils;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnOkStatusWithResponseWhenRegistrationIsSuccessful() throws Exception {
        RegistrationRequestDto requestDTO = RegistrationDTOUtils.getPreparedRequestDto();

        String token = TokenUtils.getPreparedToken();
        given(userService.registerNewUser(any())).willReturn(token);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.token").value(token));
    }

    @Test
    void shouldReturnOkStatusWithErrorWhenRegistrationFailsDueToDuplicateEmail() throws Exception {
        RegistrationRequestDto requestDTO = RegistrationDTOUtils.getPreparedRequestDto();

        when(userService.registerNewUser(requestDTO)).
                thenThrow(new RegistrationException("Duplicate email", 3001));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.error").value(3001));
    }
}
