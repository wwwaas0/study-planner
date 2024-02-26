package com.example.studyPlanner.comment.dto;

import lombok.Data;

@Data
public class CreateCommentRequest {
    private Long userId;
//    private Long postId;
    private String content;

    public CreateCommentRequest(){
        this.userId = 1L;
    }
}
