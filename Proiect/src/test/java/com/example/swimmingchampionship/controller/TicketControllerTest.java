package com.example.swimmingchampionship.controller;

import com.example.swimmingchampionship.model.Session;
import com.example.swimmingchampionship.model.Ticket;
import com.example.swimmingchampionship.model.User;
import com.example.swimmingchampionship.service.TicketService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TicketController.class)
class TicketControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TicketService ticketService;

    @Test
    @DisplayName("Buy tickets happy path")
    void buyTicketsTest() throws Exception {
        int userId = 1;
        User user = new User();
        user.setId(userId);
        int sessionId = 1;
        Session session = new Session();
        session.setId(sessionId);
        int quantity = 2;
        Ticket ticket = new Ticket(quantity, session, user);
        when(ticketService.buyTickets(userId, sessionId, quantity)).thenReturn(ticket);
        String url = "/ticket/buy/{userId}/{sessionId}";

        mockMvc.perform(post(url, userId, sessionId).param("quantity", String.valueOf(quantity)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.session.id").value(session.getId()))
                .andExpect(jsonPath("$.quantity").value(quantity));
    }

    @Test
    @DisplayName("Buy tickets invalid param")
    void buyTicketsExceptionTest() throws Exception {
        int userId = 1;
        int sessionId = 1;
        int quantity = 0;
        String url = "/ticket/buy/{userId}/{sessionId}";

        MvcResult requestResult = mockMvc.perform(post(url, userId, sessionId).param("quantity", String.valueOf(quantity)))
                .andExpect(status().isBadRequest()).andReturn();

        String result = requestResult.getResponse().getContentAsString();
        assertEquals("Minimum quantity is 1!", result);
    }
}