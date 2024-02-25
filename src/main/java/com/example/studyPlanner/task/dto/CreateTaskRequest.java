package com.example.studyPlanner.task.dto;

import lombok.Data;

@Data
public class CreateTaskRequest {
    private Long postId;
    private String content;
}
