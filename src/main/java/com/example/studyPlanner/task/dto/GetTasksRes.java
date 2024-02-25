package com.example.studyPlanner.task.dto;

import com.example.studyPlanner.task.entity.TaskStatus;
import lombok.Data;

@Data
public class GetTasksRes {
    private TaskStatus taskStatus;
    private String content;
}

