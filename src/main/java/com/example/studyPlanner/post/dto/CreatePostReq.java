package com.example.studyPlanner.post.dto;

import lombok.Getter;

@Getter
public class CreatePostReq {
    private Long userId;
    private Long boardId;
    private String content;
}
