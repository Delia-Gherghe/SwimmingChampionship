package com.example.swimmingchampionship.repository;

import com.example.swimmingchampionship.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Integer> {
}
