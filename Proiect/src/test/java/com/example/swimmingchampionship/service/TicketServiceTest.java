package com.example.swimmingchampionship.service;

import com.example.swimmingchampionship.model.Session;
import com.example.swimmingchampionship.model.Ticket;
import com.example.swimmingchampionship.model.User;
import com.example.swimmingchampionship.repository.TicketRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TicketServiceTest {
    @InjectMocks
    private TicketService ticketService;

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private UserService userService;

    @Mock
    private SessionService sessionService;


    @Test
    @DisplayName("Buy ticket")
    void buyTicketsTest(){
        int userId = 1;
        int sessionId = 1;
        int quantity = 3;
        Session session = new Session(LocalDateTime.of(2022, 6, 10, 18, 0), 20.0, 3000);
        session.setId(sessionId);
        User user = new User("User", "user@gmail.com");
        user.setId(userId);
        Ticket ticket = new Ticket(quantity, session, user);
        when(userService.getUserById(userId)).thenReturn(user);
        when(sessionService.getSessionById(sessionId)).thenReturn(session);
        when(sessionService.saveSession(session)).thenReturn(session);
        when(ticketRepository.save(any(Ticket.class))).thenReturn(ticket);

        Ticket result = ticketService.buyTickets(userId, sessionId, quantity);

        assertNotNull(result);
        assertEquals(ticket.getSession().getTicketsLeft(), result.getSession().getTicketsLeft());
        assertEquals(user.getId(), result.getUser().getId());
        assertEquals(session.getId(), result.getSession().getId());

        verify(userService).getUserById(userId);
        verify(sessionService).getSessionById(sessionId);
        verify(sessionService).saveSession(session);

    }
}