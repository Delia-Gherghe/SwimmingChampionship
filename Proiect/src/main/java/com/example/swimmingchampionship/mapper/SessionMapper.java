package com.example.swimmingchampionship.mapper;

import com.example.swimmingchampionship.dto.RaceDto;
import com.example.swimmingchampionship.dto.SessionDto;
import com.example.swimmingchampionship.model.Session;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SessionMapper {
    public SessionDto sessionToSessionDto(Session session, List<RaceDto> raceList){
        return new SessionDto(session.getId(), session.getDateTime(), session.getPrice(), session.getTicketsLeft(),
                raceList);
    }
}
