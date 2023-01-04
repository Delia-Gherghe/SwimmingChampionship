package com.example.swimmingchampionship.service;

import com.example.swimmingchampionship.exception.SponsorIdNotFoundException;
import com.example.swimmingchampionship.model.Sponsor;
import com.example.swimmingchampionship.repository.SponsorRepository;
import org.springframework.stereotype.Service;

@Service
public class SponsorService {
    private final SponsorRepository sponsorRepository;

    public SponsorService(SponsorRepository sponsorRepository) {
        this.sponsorRepository = sponsorRepository;
    }

    public Sponsor getSponsorById(int sponsorId){
        return sponsorRepository.findById(sponsorId).orElseThrow(() ->
                new SponsorIdNotFoundException("Sponsor with id " + sponsorId + " does not exist!"));
    }
}
