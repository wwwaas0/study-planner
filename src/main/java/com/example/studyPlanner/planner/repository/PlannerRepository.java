package com.example.studyPlanner.planner.repository;

import com.example.studyPlanner.planner.entity.Planner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlannerRepository extends JpaRepository<Planner, Long> {
}
