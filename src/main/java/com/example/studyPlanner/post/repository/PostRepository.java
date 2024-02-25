package com.example.studyPlanner.post.repository;

import com.example.studyPlanner.board.entity.Board;
import com.example.studyPlanner.post.entity.Post;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByOrderByCreatedAtDesc(PageRequest pageRequest);

    List<Post> findByBoardIdOrderByCreatedAtDesc(Long boardId, PageRequest pageRequest);

    List<Post> findByBoard(Board board);
    List<Post> findByContentContaining(String searchWord, PageRequest pageRequest);
}
