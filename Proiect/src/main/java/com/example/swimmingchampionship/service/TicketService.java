package com.example.swimmingchampionship.service;

import com.example.swimmingchampionship.model.Session;
import com.example.swimmingchampionship.model.User;
import com.example.swimmingchampionship.repository.TicketRepository;
import org.springframework.stereotype.Service;
import com.example.swimmingchampionship.model.Ticket;

@Service
public class TicketService {
    private final TicketRepository ticketRepository;
    private final UserService userService;
    private final SessionService sessionService;

    public TicketService(TicketRepository ticketRepository, UserService userService, SessionService sessionService) {
        this.ticketRepository = ticketRepository;
        this.userService = userService;
        this.sessionService = sessionService;
    }

    public Ticket buyTickets(int userId, int sessionId, int quantity){
        User user = userService.getUserById(userId);
        Session session = sessionService.getSessionById(sessionId);
        session.setTicketsLeft(session.getTicketsLeft() - quantity);
        Session updatedSession = sessionService.saveSession(session);
        Ticket ticket = new Ticket(quantity, updatedSession, user);
        return ticketRepository.save(ticket);
    }
}
