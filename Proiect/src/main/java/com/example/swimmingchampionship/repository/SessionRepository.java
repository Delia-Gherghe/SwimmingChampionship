package com.example.swimmingchampionship.repository;

import com.example.swimmingchampionship.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepository extends JpaRepository<Session, Integer> {
}
