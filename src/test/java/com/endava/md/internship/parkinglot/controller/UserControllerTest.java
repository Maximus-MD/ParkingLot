package com.endava.md.internship.parkinglot.controller;

import com.endava.md.internship.parkinglot.dto.ResponseMessageDTO;
import com.endava.md.internship.parkinglot.service.UserService;
import com.endava.md.internship.parkinglot.utils.EmailDTOUtils;
import com.endava.md.internship.parkinglot.utils.ResponseDTOUtils;
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

@ExtendWith(MockitoExtension.class)
class UserControllerTest {
    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void switchUserRoleToAdminTest_ReturnsRoleSwitchedSuccessfulResponseDto() throws Exception {
        final String ADMIN_ROLE = "ROLE_ADMIN";
        EmailDTOUtils.UserEmail emailDTO = EmailDTOUtils.getPreparedDTO();
        ResponseMessageDTO responseDTO = ResponseDTOUtils.getPreparedResponseMessageForRoleDTO(
                emailDTO.email(), ADMIN_ROLE);

        when(userService.setNewRole(any(), any())).thenReturn(responseDTO);

        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/users/switch-role/admin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(emailDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(emailDTO.email()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value(ADMIN_ROLE));
    }

    @Test
    void switchUserRoleToRegularTest_ReturnsRoleSwitchedSuccessfulResponseDto() throws Exception {
        final String USER_ROLE = "ROLE_USER";
        EmailDTOUtils.UserEmail emailDTO = EmailDTOUtils.getPreparedDTO();
        ResponseMessageDTO responseDTO = ResponseDTOUtils.getPreparedResponseMessageForRoleDTO(
                emailDTO.email(), USER_ROLE);

        when(userService.setNewRole(any(), any())).thenReturn(responseDTO);

        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/users/switch-role/regular")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(emailDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(emailDTO.email()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value(USER_ROLE));
    }
}