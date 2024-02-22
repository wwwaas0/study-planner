package com.example.studyPlanner.post.service;

import com.example.studyPlanner.board.entity.Board;
import com.example.studyPlanner.board.repository.BoaordRepository;
import com.example.studyPlanner.comment.repository.CommentRepository;
import com.example.studyPlanner.planner.entity.Planner;
import com.example.studyPlanner.planner.repository.PlannerRepository;
import com.example.studyPlanner.post.entity.Post;
import com.example.studyPlanner.post.repository.PostRepository;
import com.example.studyPlanner.user.entity.User;
import com.example.studyPlanner.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    private final UserRepository userRepository;
    private final BoaordRepository boardRepository;
    private final PlannerRepository plannerRepository;
    private final CommentRepository commentRepository;


    public List<Post> getAllPosts(int page) {
        int pageSize = 10;
        PageRequest pageRequest = PageRequest.of(page, pageSize, Sort.by("createdAt").descending());
        return postRepository.findAllByOrderByCreatedAtDesc(pageRequest);
    }

    public List<Post> getPosts(Long boardId, int page) {
        int pageSize = 10;
        PageRequest pageRequest = PageRequest.of(page, pageSize, Sort.by("createdAt").descending());
        return postRepository.findByBoardIdOrderByCreatedAtDesc(boardId, pageRequest);
    }


    public Post getPost(Long postId) {
        Optional<Post> postOptional = postRepository.findById(postId);
        Post post = null;

        if (postOptional.isPresent()) {
            post = postOptional.get();
            return post;
        } else {
            throw new EntityNotFoundException(postId + "번 게시글이 존재하지 않습니다.");
        }

    }


    public List<Post> searchPosts(String search) { // 할일 내용 또는 게시글의 내용
        //TODO: 할 일의 content도 검색 가능하게
        return postRepository.findByContentContaining(search);
    }

    public Post createPost(Long plannerId, Long userId, Long boardId, String content) {
        Optional<Planner> plannerOptional = plannerRepository.findById(plannerId);
        Optional<com.example.studyPlanner.user.entity.User> userOptional = userRepository.findById(userId);
        Optional<Board> boardOptional = boardRepository.findById(boardId);
        Post post = null;

        if (userOptional.isPresent() && boardOptional.isPresent() && plannerOptional.isPresent()) {
            Planner planner = plannerOptional.get();
            User user = userOptional.get();
            Board board = boardOptional.get();

            post = new Post(user, board, planner, content);
            postRepository.save(post);
        } else if (!userOptional.isPresent()) {
            throw new EntityNotFoundException(userId + "번 유저가 존재하지 않습니다.");
        } else if (!boardOptional.isPresent()) {
            throw new EntityNotFoundException(boardId + "번 게시판이 존재하지 않습니다.");
        } else {
            throw new EntityNotFoundException(plannerId + "번 플래너가 존재하지 않습니다.");
        }
        return post;
    }

    @Transactional
    public Post updatePost(Long postId, String newContent) {
        Optional<Post> postOptional = postRepository.findById(postId);
        Post post = null;
        if (postOptional.isPresent()) {
            post = postOptional.get();
            post.setContent(newContent);
            postRepository.save(post);
        } else {
            throw new EntityNotFoundException(postId + "번 게시글이 존재하지 않습니다.");
        }
        return post;
    }

    @Transactional
    public String deletePost(Long postId) {
        Optional<Post> postOptional = postRepository.findById(postId);
        if (postOptional.isPresent()) {
            log.info("게시글의 댓글이 함께 삭제됩니다.");
            postRepository.deleteById(postId);
            commentRepository.deleteById(postId);
        } else {
            throw new EntityNotFoundException(postId + "번 게시글이 존재하지 않습니다.");
        }
        return postId + "번 게시물이 정상적으로 삭제되었습니다.";
    }
}
