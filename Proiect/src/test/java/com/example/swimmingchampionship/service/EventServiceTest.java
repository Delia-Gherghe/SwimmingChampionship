package com.example.swimmingchampionship.service;

import com.example.swimmingchampionship.exception.EventIdNotFoundException;
import com.example.swimmingchampionship.model.Event;
import com.example.swimmingchampionship.repository.EventRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EventServiceTest {

    @InjectMocks
    private EventService eventService;

    @Mock
    private EventRepository eventRepository;

    @Test
    @DisplayName("Get event by id happy path")
    void getEventByIdTest() {
        int id = 1;
        Event event = new Event();
        event.setId(id);
        when(eventRepository.findById(id)).thenReturn(Optional.of(event));

        Event result = eventService.getEventById(id);

        assertNotNull(result);
        assertEquals(event.getId(), result.getId());

        verify(eventRepository).findById(id);
    }

    @Test
    @DisplayName("Get event by id not found")
    void getEventByIdExceptionTest(){
        int id = 1;
        when(eventRepository.findById(id)).thenReturn(Optional.empty());

        EventIdNotFoundException exception = assertThrows(EventIdNotFoundException.class,
                () -> eventService.getEventById(id));

        assertNotNull(exception);
        assertEquals("Event with id 1 does not exist", exception.getMessage());

        verify(eventRepository).findById(id);
    }
}