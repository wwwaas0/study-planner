package com.example.studyPlanner.domain.post.entity;

import com.example.studyPlanner.global.entity.BaseEntity;
import com.example.studyPlanner.domain.board.entity.Board;
import com.example.studyPlanner.domain.planner.entity.Planner;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.catalina.User;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "post")
public class Post extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @Column
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "planner_id", nullable = false)
    private Planner planner;

    public Post(User user, Board board, Planner planner, String content) {
        this.user = user;
        this.board = board;
        this.planner = planner;
        this.content = content;
    }
}