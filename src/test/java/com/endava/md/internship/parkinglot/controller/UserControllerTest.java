package com.endava.md.internship.parkinglot.controller;

import com.endava.md.internship.parkinglot.dto.RoleSwitchResponseDto;
import com.endava.md.internship.parkinglot.service.UserService;
import com.endava.md.internship.parkinglot.utils.EmailDTOUtils;
import com.endava.md.internship.parkinglot.utils.RoleSwitchDTOUtils;
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
class UserControllerTest {

    private static final String EMAIL = "AlexTests@gmail.com";

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
        RoleSwitchResponseDto roleSwitchResponseDto = RoleSwitchDTOUtils.getPreparedForAdminResponseDTO();

        when(userService.setNewRole(any(), any())).thenReturn(roleSwitchResponseDto);

        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/users/switch-role/admin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(EmailDTOUtils.getPreparedDTO())))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(EMAIL))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.newRole").value("ROLE_ADMIN"))
                .andDo(print());
    }

    @Test
    void switchUserRoleToRegularTest_ReturnsRoleSwitchedSuccessfulResponseDto() throws Exception {
        RoleSwitchResponseDto roleSwitchResponseDto = RoleSwitchDTOUtils.getPreparedForRegularResponseDTO();

        when(userService.setNewRole(any(), any())).thenReturn(roleSwitchResponseDto);

        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/users/switch-role/regular")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(EmailDTOUtils.getPreparedDTO())))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(EMAIL))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.newRole").value("ROLE_REGULAR"))
                .andDo(print());
    }
}