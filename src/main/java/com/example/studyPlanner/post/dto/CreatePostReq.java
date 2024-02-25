package com.example.studyPlanner.post.dto;

import lombok.Data;
import lombok.Getter;

import java.time.LocalDate;

@Data
public class CreatePostReq {
    private LocalDate createdAt;
    private String boardName;
    private String content;
}
