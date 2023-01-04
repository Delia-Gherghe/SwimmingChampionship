package com.example.swimmingchampionship.mapper;

import com.example.swimmingchampionship.dto.SwimmerRequest;
import com.example.swimmingchampionship.model.Swimmer;
import org.springframework.stereotype.Component;

@Component
public class SwimmerMapper {
    public Swimmer swimmerRequestToSwimmer(SwimmerRequest swimmerRequest){
        return new Swimmer(swimmerRequest.getFirstName(), swimmerRequest.getLastName(),
                swimmerRequest.getBirthday(), swimmerRequest.getCountry());
    }
}
