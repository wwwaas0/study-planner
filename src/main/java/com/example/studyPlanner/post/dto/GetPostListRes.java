package com.example.studyPlanner.post.dto;

import com.example.studyPlanner.task.entity.Task;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class GetPostListRes {
    private Long postId;
    private LocalDateTime createdAt;
    private String studyTime;
    private List<Task> topThreeTask;
}
