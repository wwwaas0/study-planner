package com.example.studyPlanner.domain.task.entity;

import com.example.studyPlanner.global.entity.BaseEntity;
import com.example.studyPlanner.domain.planner.entity.Planner;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "task")
public class Task extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TaskStatus taskStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "planner_id", nullable = false)
    private Planner planner;

    public Task(String content, Planner planner) {
        this.content = content;
        this.planner = planner;
        this.taskStatus = TaskStatus.NOT_STARTED;
    }
}
