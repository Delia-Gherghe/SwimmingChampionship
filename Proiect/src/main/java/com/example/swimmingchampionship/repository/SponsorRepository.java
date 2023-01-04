package com.example.swimmingchampionship.repository;

import com.example.swimmingchampionship.model.Sponsor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SponsorRepository extends JpaRepository<Sponsor, Integer> {
}
