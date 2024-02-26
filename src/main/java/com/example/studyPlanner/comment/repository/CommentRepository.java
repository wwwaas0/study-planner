package com.example.studyPlanner.comment.repository;

import com.example.studyPlanner.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostIdOrderByCreatedAtDesc(Long postId);
    Comment findByid(Long commentId);
    boolean existsById(Long commentId);
}
