package com.example.swimmingchampionship.mapper;

import com.example.swimmingchampionship.dto.FinalStageRequest;
import com.example.swimmingchampionship.dto.HeatRequest;
import com.example.swimmingchampionship.model.Race;
import com.example.swimmingchampionship.model.RoundType;
import org.springframework.stereotype.Component;

@Component
public class RaceRequestMapper {
    public Race heatRequestToRace(HeatRequest heatRequest){
        return new Race(heatRequest.getName(), RoundType.Heat, heatRequest.getStartTime());
    }

    public Race finalStageToRace(FinalStageRequest finalStageRequest){
        return new Race(finalStageRequest.getName(), finalStageRequest.getRound(), finalStageRequest.getStartTime());
    }
}
