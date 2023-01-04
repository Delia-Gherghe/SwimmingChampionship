package com.example.swimmingchampionship.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.swimmingchampionship.model.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {
}
