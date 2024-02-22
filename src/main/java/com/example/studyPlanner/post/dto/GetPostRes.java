package com.example.studyPlanner.post.dto;

import com.example.studyPlanner.task.entity.Task;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class GetPostRes {
    //    유저 이름, 게시글 생성 날짜, 플래너 생성 날짜, 총 공부 시간, 할일 리스트
    private String name;
    private LocalDateTime postCreatedAt;
    private LocalDateTime plannerCreatedAt;
    private int studyTime;
    private List<Task> tasks;
}
