package com.example.studyPlanner.planner.entity;

import com.example.studyPlanner.user.entity.User;
import com.example.studyPlanner.global.entity.BaseEntity;
import com.example.studyPlanner.task.entity.Task;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "planner")
public class Planner extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="planner_id")
    private Long id;

    @Column(nullable=false, columnDefinition="INT DEFAULT 0")
    private int studyTime;

    @Column(nullable = true)
    private LocalDateTime studyStart;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "planner")
    private List<Task> tasks = new ArrayList<>();

}