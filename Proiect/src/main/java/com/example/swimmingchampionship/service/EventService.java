package com.example.swimmingchampionship.service;

import com.example.swimmingchampionship.exception.EventIdNotFoundException;
import com.example.swimmingchampionship.model.Event;
import com.example.swimmingchampionship.repository.EventRepository;
import org.springframework.stereotype.Service;

@Service
public class EventService {
    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public Event getEventById(int eventId){
        return eventRepository.findById(eventId).orElseThrow(() ->
                new EventIdNotFoundException("Event with id " + eventId + " does not exist"));
    }
}
