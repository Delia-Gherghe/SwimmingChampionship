package com.example.swimmingchampionship.service;

import com.example.swimmingchampionship.dto.RaceDto;
import com.example.swimmingchampionship.dto.SessionDto;
import com.example.swimmingchampionship.exception.SessionIdNotFoundException;
import com.example.swimmingchampionship.mapper.RaceMapper;
import com.example.swimmingchampionship.mapper.SessionMapper;
import com.example.swimmingchampionship.model.Race;
import com.example.swimmingchampionship.model.RoundType;
import com.example.swimmingchampionship.model.Session;
import com.example.swimmingchampionship.repository.SessionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SessionServiceTest {
    @InjectMocks
    private SessionService sessionService;

    @Mock
    private SessionRepository sessionRepository;

    @Mock
    private RaceMapper raceMapper;

    @Mock
    private SessionMapper sessionMapper;

    @Test
    @DisplayName("Get session by id happy path")
    void getSessionByIdTest(){
        int id = 1;
        Session session = new Session();
        session.setId(id);
        when(sessionRepository.findById(id)).thenReturn(Optional.of(session));

        Session result = sessionService.getSessionById(id);

        assertNotNull(result);
        assertEquals(session.getId(), result.getId());

        verify(sessionRepository).findById(id);
    }

    @Test
    @DisplayName("Get session by id not found")
    void getSessionByIdExceptionTest(){
        int id = 1;
        when(sessionRepository.findById(id)).thenReturn(Optional.empty());

        SessionIdNotFoundException exception = assertThrows(SessionIdNotFoundException.class,
                () -> sessionService.getSessionById(id));

        assertNotNull(exception);
        assertEquals("Session with id 1 does not exist!", exception.getMessage());

        verify(sessionRepository).findById(id);
    }

    @Test
    @DisplayName("Get session by id with race schedule")
    void getSessionDtoByIdTest(){
        int id = 1;
        Race race1 = new Race("Heat 1", RoundType.Heat, "09:10");
        Race race2 = new Race("Heat 2", RoundType.Heat, "09:20");
        Race race3 = new Race("Heat 3", RoundType.Heat, "09:00");
        List<Race> races = List.of(race1, race2, race3);
        Session session = new Session(LocalDateTime.of(2022, 6, 10, 18, 0), 20.0, 3000);
        session.setId(id);
        session.setRaceList(races);
        when(sessionRepository.findById(id)).thenReturn(Optional.of(session));
        RaceDto raceDto1 = new RaceDto("Heat 1", RoundType.Heat, "09:10");
        RaceDto raceDto2 = new RaceDto("Heat 2", RoundType.Heat, "09:20");
        RaceDto raceDto3 = new RaceDto("Heat 3", RoundType.Heat, "09:00");
        when(raceMapper.RaceToRaceDto(race1)).thenReturn(raceDto1);
        when(raceMapper.RaceToRaceDto(race2)).thenReturn(raceDto2);
        when(raceMapper.RaceToRaceDto(race3)).thenReturn(raceDto3);
        List<RaceDto> raceDtoList = List.of(raceDto3, raceDto1, raceDto2);
        SessionDto sessionDto = new SessionDto(id, LocalDateTime.of(2022, 6, 10, 18, 0), 20.0, 3000, raceDtoList);
        when(sessionMapper.sessionToSessionDto(session, raceDtoList)).thenReturn(sessionDto);

        SessionDto result = sessionService.getSessionDtoById(id);

        assertNotNull(result);
        assertEquals(sessionDto.getId(), result.getId());
        assertEquals(sessionDto.getRaceList().get(0).getStartTime(), result.getRaceList().get(0).getStartTime());
        assertEquals(sessionDto.getRaceList().get(1).getStartTime(), result.getRaceList().get(1).getStartTime());
        assertEquals(sessionDto.getRaceList().get(2).getStartTime(), result.getRaceList().get(2).getStartTime());

        verify(sessionRepository).findById(id);
        verify(sessionMapper).sessionToSessionDto(session, raceDtoList);
    }

    @Test
    @DisplayName("Save session")
    void saveSessionTest(){
        Session session = new Session(LocalDateTime.of(2022, 6, 10, 18, 0), 20.0, 3000);
        Session savedSession = new Session(LocalDateTime.of(2022, 6, 10, 18, 0), 20.0, 3000);
        when(sessionRepository.save(session)).thenReturn(savedSession);

        Session result = sessionService.saveSession(session);

        assertNotNull(result);
        assertEquals(savedSession.getDateTime(), result.getDateTime());
        assertEquals(savedSession.getPrice(), result.getPrice());
        assertEquals(savedSession.getTicketsLeft(), result.getTicketsLeft());

        verify(sessionRepository).save(session);
    }
}