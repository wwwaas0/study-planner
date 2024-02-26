package com.example.studyPlanner.comment.service;

import com.example.studyPlanner.comment.dto.GetCommentListRes;
import com.example.studyPlanner.comment.entity.Comment;
import com.example.studyPlanner.comment.mapper.CommentMapper;
import com.example.studyPlanner.comment.repository.CommentRepository;
import com.example.studyPlanner.post.entity.Post;
import com.example.studyPlanner.post.repository.PostRepository;
import com.example.studyPlanner.user.entity.User;
import com.example.studyPlanner.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public List<GetCommentListRes> getComments(Long postId) {
        List<Comment> comments = commentRepository.findByPostIdOrderByCreatedAtDesc(postId);
        List<GetCommentListRes> commentListRes = comments.stream()
                .map(comment -> CommentMapper.INSTANCE.toDTO(comment))
                .collect(Collectors.toList());

        return commentListRes;
    }

    public Post getPost(Long commentId) {
        Comment comment = commentRepository.findByid(commentId);
        return comment.getPost();
    }

    public void createComment(Long postId, Long userId, String content) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new EntityNotFoundException(postId + "번 게시글이 존재하지 않습니다."));
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException(userId + "번 유저가 존재하지 않습니다."));

        Comment comment = new Comment(content, user, post);
        commentRepository.save(comment);
    }

    @Transactional
    public Comment updateComment(Long commentId, String newContent) {
        Optional<Comment> commentOptional = commentRepository.findById(commentId);
        Comment comment = null;
        if (commentOptional.isPresent()) {
            comment = commentOptional.get();
            comment.setContent(newContent);
            commentRepository.save(comment);
        } else {
            throw new EntityNotFoundException(commentId + "번 댓글이 존재하지 않습니다.");
        }
        return comment;
    }


    public void deleteComment(Long commentId) {
        if (commentRepository.existsById(commentId)) {
            commentRepository.deleteById(commentId);
        } else {
            throw new EntityNotFoundException(commentId + "번 댓글이 존재하지 않습니다.");
        }
    }
}
