package com.example.studyPlanner.planner.dto;

import com.example.studyPlanner.task.entity.Task;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class GetPlannerHomeRes {
    private LocalDateTime createdAt;
    private int studyTime;
    private List<Task> tasks;
}
