package com.example.swimmingchampionship.mapper;

import com.example.swimmingchampionship.dto.RaceDto;
import com.example.swimmingchampionship.model.Race;
import org.springframework.stereotype.Component;

@Component
public class RaceMapper {
    public RaceDto RaceToRaceDto(Race race){
        return new RaceDto(race.getId(), race.getName(), race.getRound(),
                race.getEvent().getDistance() + " " + race.getEvent().getStyle(), race.getStartTime(),
                race.getSwimmerLane1().getFirstName() + " " + race.getSwimmerLane1().getLastName(),
                race.getTimeLane1(),
                race.getSwimmerLane2().getFirstName() + " " + race.getSwimmerLane2().getLastName(),
                race.getTimeLane2(),
                race.getSwimmerLane3().getFirstName() + " " + race.getSwimmerLane3().getLastName(),
                race.getTimeLane3(),
                race.getSwimmerLane4().getFirstName() + " " + race.getSwimmerLane4().getLastName(),
                race.getTimeLane4());
    }
}
