package com.endava.md.internship.parkinglot.controller;

import com.endava.md.internship.parkinglot.service.PasswordRestorationService;
import com.endava.md.internship.parkinglot.utils.EmailDTOUtils;
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

@ExtendWith(MockitoExtension.class)
class PasswordRestorationControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PasswordRestorationService passwordRestorationService;

    @InjectMocks
    private PasswordRestorationController passwordRestorationController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(passwordRestorationController).build();
    }

    @Test
    void passwordRestorationTest_ReturnsSuccessfulResponseEntity() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/restore-password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(EmailDTOUtils.getPreparedDTO())))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value("Restored"));
    }
}
