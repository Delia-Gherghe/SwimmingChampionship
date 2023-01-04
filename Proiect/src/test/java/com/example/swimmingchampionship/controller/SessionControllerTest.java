package com.example.swimmingchampionship.controller;

import com.example.swimmingchampionship.dto.SessionDto;
import com.example.swimmingchampionship.service.SessionService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SessionController.class)
class SessionControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SessionService sessionService;

    @Test
    @DisplayName("Get session schedule by id")
    void getSessionByIdTest() throws Exception {
        int id = 1;
        SessionDto session = new SessionDto();
        session.setId(id);
        when(sessionService.getSessionDtoById(id)).thenReturn(session);
        String url = "/session/{sessionId}/schedule";

        mockMvc.perform(get(url, id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(session.getId()));
    }
}