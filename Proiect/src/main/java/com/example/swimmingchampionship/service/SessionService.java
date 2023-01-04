package com.example.swimmingchampionship.service;

import com.example.swimmingchampionship.dto.RaceDto;
import com.example.swimmingchampionship.dto.SessionDto;
import com.example.swimmingchampionship.exception.SessionIdNotFoundException;
import com.example.swimmingchampionship.mapper.RaceMapper;
import com.example.swimmingchampionship.mapper.SessionMapper;
import com.example.swimmingchampionship.model.Session;
import com.example.swimmingchampionship.repository.SessionRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SessionService {
    private final SessionRepository sessionRepository;
    private final RaceMapper raceMapper;
    private final SessionMapper sessionMapper;

    public SessionService(SessionRepository sessionRepository, RaceMapper raceMapper, SessionMapper sessionMapper) {
        this.sessionRepository = sessionRepository;
        this.raceMapper = raceMapper;
        this.sessionMapper = sessionMapper;
    }

    public Session getSessionById(int sessionId){
        return sessionRepository.findById(sessionId).orElseThrow(() ->
                new SessionIdNotFoundException("Session with id " + sessionId + " does not exist!"));
    }

    public SessionDto getSessionDtoById(int sessionId){
        Session session = getSessionById(sessionId);
        List<RaceDto> raceDtoList = session.getRaceList()
                .stream()
                .map(raceMapper::RaceToRaceDto)
                .sorted(Comparator.comparing(RaceDto::getStartTime))
                .collect(Collectors.toList());
        return sessionMapper.sessionToSessionDto(session, raceDtoList);
    }

    public Session saveSession(Session session){
        return sessionRepository.save(session);
    }
}
