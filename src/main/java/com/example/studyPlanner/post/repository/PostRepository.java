package com.example.studyPlanner.post.repository;

import com.example.studyPlanner.board.entity.Board;
import com.example.studyPlanner.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findAll(Pageable pageable);

    Page<Post> findByBoardId(Long boardId, Pageable pageable);

    List<Post> findByBoard(Board board);
    Page<Post> findByContentContaining(String searchWord, Pageable pageable);
}
