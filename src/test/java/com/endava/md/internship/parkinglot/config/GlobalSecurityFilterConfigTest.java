package com.endava.md.internship.parkinglot.config;

import com.endava.md.internship.parkinglot.dto.LoginRequestDto;
import com.endava.md.internship.parkinglot.dto.LoginResponseDto;
import com.endava.md.internship.parkinglot.service.AuthService;
import com.endava.md.internship.parkinglot.utils.LoginDTOUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = {"spring.flyway.enabled=false"})
@AutoConfigureMockMvc
class GlobalSecurityFilterConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void securityFilterChainTest_AccessingSecuredEndpoint_ReturnsSuccessFalseAnd4044ErrorCode() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/something-protected")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.token").value((Object) null))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error").value(4044))
                .andDo(print());
    }

    @Test
    void securityFilterChainTest_AccessingUnsecuredEndpoint_ReturnsSuccessTrueAndNullError() throws Exception {
        LoginRequestDto loginRequestDto = LoginDTOUtils.getPreparedRequestDto();
        LoginResponseDto loginResponseDto = LoginDTOUtils.getPreparedResponseDto();

        when(authService.login(any())).thenReturn(loginResponseDto);

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