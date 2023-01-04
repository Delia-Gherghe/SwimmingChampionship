package com.example.swimmingchampionship.service;

import com.example.swimmingchampionship.dto.SwimmerRequest;
import com.example.swimmingchampionship.exception.SwimmerIdNotFoundException;
import com.example.swimmingchampionship.mapper.SwimmerMapper;
import com.example.swimmingchampionship.model.Swimmer;
import com.example.swimmingchampionship.repository.SwimmerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SwimmerService {
    private final SwimmerRepository swimmerRepository;
    private final SwimmerMapper swimmerMapper;

    public SwimmerService(SwimmerRepository swimmerRepository, SwimmerMapper swimmerMapper) {
        this.swimmerRepository = swimmerRepository;
        this.swimmerMapper = swimmerMapper;
    }

    public Swimmer saveSwimmer(SwimmerRequest swimmerRequest){
        return swimmerRepository.save(swimmerMapper.swimmerRequestToSwimmer(swimmerRequest));
    }

    public List<Swimmer> getSwimmers(String country){
        if (country == null){
            return swimmerRepository.findAll();
        } else {
            return swimmerRepository.getSwimmerByCountry(country);
        }
    }

    public Swimmer getSwimmerById(int swimmerId){
        return swimmerRepository.findById(swimmerId).orElseThrow(() ->
                new SwimmerIdNotFoundException("Swimmer with id " + swimmerId + " does not exist!"));
    }
}
