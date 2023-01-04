package com.example.swimmingchampionship.repository;

import com.example.swimmingchampionship.model.Swimmer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SwimmerRepository extends JpaRepository<Swimmer, Integer> {
    List<Swimmer> getSwimmerByCountry(String country);
}
