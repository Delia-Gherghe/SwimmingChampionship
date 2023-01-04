package com.example.swimmingchampionship.repository;

import com.example.swimmingchampionship.model.Event;
import com.example.swimmingchampionship.model.Race;
import com.example.swimmingchampionship.model.RoundType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RaceRepository extends JpaRepository<Race, Integer> {
    List<Race> getRaceByEventAndRoundOrderByStartTime(Event event, RoundType roundType);

    List<Race> getRaceByEventAndRound(Event event, RoundType roundType);
}
