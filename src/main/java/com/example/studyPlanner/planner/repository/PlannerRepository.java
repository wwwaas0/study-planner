package com.example.studyPlanner.planner.repository;

import com.example.studyPlanner.planner.entity.Planner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface PlannerRepository extends JpaRepository<Planner, Long> {
    Optional<Planner> findByUserIdAndCreatedAtBetween(Long userId, LocalDateTime startOfDay, LocalDateTime endOfDay);
    Optional<Planner> findByCreatedAtBetween(LocalDateTime startOfDay, LocalDateTime endOfDay);
}
