package com.example.studyPlanner.task.repository;

import com.example.studyPlanner.task.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByPlannerId(Long plannerId);
}
