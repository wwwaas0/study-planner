package com.example.studyPlanner.service;

import com.example.studyPlanner.comment.dto.GetCommentListRes;
import com.example.studyPlanner.comment.entity.Comment;
import com.example.studyPlanner.comment.repository.CommentRepository;
import com.example.studyPlanner.comment.service.CommentService;
import com.example.studyPlanner.planner.entity.Planner;
import com.example.studyPlanner.post.entity.Post;
import com.example.studyPlanner.post.repository.PostRepository;
import com.example.studyPlanner.user.entity.User;
import com.example.studyPlanner.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private PostRepository postRepository;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CommentService commentService;

    @Test
    @DisplayName("댓글 목록 조회")
    void getComments() {
        //given
        Long postId = 1L;

        User user = new User();
        Post post = new Post();
        post.setId(1L);

        Comment comment1 = new Comment(1L, "내용1", user, post);
        Comment comment2 = new Comment(2L, "내용2", user, post);

        List<Comment> comments = new ArrayList<>();
        comments.add(comment1);
        comments.add(comment2);

        when(commentRepository.findByPostIdOrderByCreatedAtDesc(postId)).thenReturn(comments);

        //when
        List<GetCommentListRes> results = commentService.getComments(postId);

        //then
        verify(commentRepository).findByPostIdOrderByCreatedAtDesc(postId);
        assertEquals(comments.size(), results.size());

        // 각 요소의 내용 검증
        for (int i = 0; i < comments.size(); i++) {
            Comment expectedComment = comments.get(i);
            GetCommentListRes actualComment = results.get(i);

            assertEquals(expectedComment.getId(), actualComment.getCommentId());
            assertEquals(expectedComment.getContent(), actualComment.getContent());
        }
    }
    @Test
    @DisplayName("댓글 생성")
    void createComment() {
        //given
        User user = new User();
        user.setId(1L);
        user.setName("test user");

        Planner planner=new Planner();
        planner.setId(1L);
        planner.setStudyTime(0);
        planner.setUser(user);

        Post post = new Post();
        post.setId(1L);
        post.setUser(user);
        post.setPlanner(planner);

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(postRepository.findById(post.getId())).thenReturn(Optional.of(post));

        Comment comment = new Comment(1L, "내용", user, post);

        Comment savedComment = new Comment();
        savedComment.setId(comment.getId());
        savedComment.setContent(comment.getContent());
        savedComment.setUser(user);
        savedComment.setPost(post);
        when(commentRepository.save(any(Comment.class))).thenReturn(savedComment);

        //when
        Comment result = commentService.createComment(comment.getPost().getId(), comment.getUser().getId(), comment.getContent());

        //then
        verify(userRepository).findById(user.getId());
        verify(postRepository).findById(post.getId());
        verify(commentRepository).save(any(Comment.class));

        assertEquals(comment.getId(), result.getId());
        assertEquals(comment.getContent(), result.getContent());
        assertEquals(comment.getUser(), result.getUser());
        assertEquals(comment.getPost(), result.getPost());
    }

    @Test
    @DisplayName("댓글 수정")
    void updateComment() {
        //given
        User user = new User();
        Post post = new Post();
        Comment comment = new Comment(1L, "내용", user, post);
        when(commentRepository.findById(comment.getId())).thenReturn(Optional.of(comment));

        String newContent = "새로운 내용";

        Comment updatedComment = new Comment();
        updatedComment.setId(1L);
        updatedComment.setContent(newContent);
        updatedComment.setUser(user);
        updatedComment.setPost(post);
        when(commentRepository.save(comment)).thenReturn(updatedComment);

        //when
        Comment result = commentService.updateComment(comment.getId(), newContent);

        //then
        verify(commentRepository).findById(comment.getId());
        verify(commentRepository).save(comment);
        assertEquals(comment.getId(), result.getId());
        assertEquals(newContent, result.getContent());
    }

    @DisplayName("댓글 삭제")
    @Test
    void deleteComment() {
        //given
        Long deleteId = 1L;

        when(commentRepository.existsById(deleteId)).thenReturn(true);

        //when
        commentService.deleteComment(deleteId);

        //then
        verify(commentRepository).deleteById(deleteId);
    }

    @DisplayName("찾는 댓글이 없을 때는 예외처리를 한다")
    @Test
    void deleteCommentWhenCommentDoesNotExist() {
        //given
        Long deleteId = 1L;
        when(commentRepository.existsById(deleteId)).thenReturn(false);

        //when
        //then
        assertThrows(EntityNotFoundException.class, () -> {
            commentService.deleteComment(deleteId);
        });
    }

}

