package com.example.studyPlanner.comment.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GetCommentListRes {
    private Long commentId;
    private String name;
    private String content;
    private LocalDateTime createdAt;
}
