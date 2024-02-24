package com.example.studyPlanner.post.dto;

import com.example.studyPlanner.task.entity.Task;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class GetPostRes {
    private Long postId;
    private String name;
    private LocalDateTime postCreatedAt;
    private LocalDateTime plannerCreatedAt;
    private String studyTime;
    private List<Task> tasks;
    private String content;
}
