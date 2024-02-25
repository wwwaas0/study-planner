package com.example.studyPlanner.post.service;

import com.example.studyPlanner.board.entity.Board;
import com.example.studyPlanner.board.repository.BoardRepository;
import com.example.studyPlanner.comment.repository.CommentRepository;
import com.example.studyPlanner.planner.entity.Planner;
import com.example.studyPlanner.planner.repository.PlannerRepository;
import com.example.studyPlanner.post.dto.GetPostListRes;
import com.example.studyPlanner.post.dto.GetPostRes;
import com.example.studyPlanner.post.entity.Post;
import com.example.studyPlanner.post.mapper.PostMapper;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final PlannerRepository plannerRepository;
    private final CommentRepository commentRepository;

    private static final int PAGE_SIZE = 10;

    public List<GetPostListRes> getAllPosts(int page) {
        PageRequest pageRequest = createPageRequest(page);

        List<Post> posts = postRepository.findAllByOrderByCreatedAtDesc(pageRequest);
        List<GetPostListRes> postListResList = posts.stream()
                .map(post -> PostMapper.INSTANCE.toListDTO(post))
                .collect(Collectors.toList());

        return postListResList;
    }

    public List<GetPostListRes> getPosts(Long boardId, int page) {
        PageRequest pageRequest = createPageRequest(page);

        List<Post> posts = postRepository.findByBoardIdOrderByCreatedAtDesc(boardId, pageRequest);
        List<GetPostListRes> postListResList = posts.stream()
                .map(post -> PostMapper.INSTANCE.toListDTO(post))
                .collect(Collectors.toList());

        return postListResList;
    }

    private PageRequest createPageRequest(int page) {
        return PageRequest.of(page, PAGE_SIZE, Sort.by("createdAt").descending());
    }

    public GetPostRes getPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException(postId + "번 게시글이 존재하지 않습니다."));

        GetPostRes getPostRes = PostMapper.INSTANCE.toDTO(post);
        return getPostRes;
    }

    public List<GetPostListRes> searchPosts(String search, int page) { // 할일 내용 또는 게시글의 내용
        //TODO: 할 일의 content도 검색 가능하게
        PageRequest pageRequest = createPageRequest(page);
        List<Post> posts = postRepository.findByContentContaining(search, pageRequest);

        List<GetPostListRes> postListResList = posts.stream()
                .map(post -> PostMapper.INSTANCE.toListDTO(post))
                .collect(Collectors.toList());
        return postListResList;
    }

    public Post createPost(Long userId, LocalDate plannerCreatedAt, String boardName, String content) {
        LocalDateTime startOfDay = plannerCreatedAt.atStartOfDay();
        LocalDateTime endOfDay = plannerCreatedAt.atTime(LocalTime.MAX);

        Planner planner = plannerRepository.findByCreatedAtBetween(startOfDay, endOfDay)
                .orElseThrow(() -> new EntityNotFoundException(plannerCreatedAt + " 날짜에 생성된 플래너가 존재하지 않습니다."));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(userId + "번 유저가 존재하지 않습니다."));
        Board board = boardRepository.findByName(boardName)
                .orElseThrow(() -> new EntityNotFoundException(boardName + " 게시판이 존재하지 않습니다."));

        Post post = new Post(user, board, planner, content);
        return postRepository.save(post);
    }

    @Transactional
    public Post updatePost(Long postId, String newContent) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException(postId + "번 게시글이 존재하지 않습니다."));
        post.setContent(newContent);

        return postRepository.save(post);
    }

    @Transactional
    public void deletePost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new EntityNotFoundException(postId + "번 게시글이 존재하지 않습니다."));

        log.info("게시글의 댓글이 함께 삭제됩니다.");
        postRepository.deleteById(postId);
        commentRepository.deleteById(postId);
    }
}
