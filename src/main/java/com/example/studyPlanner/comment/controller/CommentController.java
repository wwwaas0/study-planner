package com.example.studyPlanner.comment.controller;

import com.example.studyPlanner.comment.dto.CreateCommentRequest;
import com.example.studyPlanner.comment.dto.GetCommentListRes;
import com.example.studyPlanner.comment.dto.UpdateCommentRequest;
import com.example.studyPlanner.comment.entity.Comment;
import com.example.studyPlanner.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("create/{postId}")
    public String createComment(@PathVariable("postId") Long postId, @ModelAttribute CreateCommentRequest createCommentRequest) {
        commentService.createComment(postId, createCommentRequest.getUserId(), createCommentRequest.getContent());

        return "redirect:/posts/" + postId;
    }

    @PostMapping("/{commentId}")
    public String updateComment(@PathVariable("commentId") Long commentId, @ModelAttribute UpdateCommentRequest updateCommentRequest) {
        Comment comment = commentService.updateComment(commentId, updateCommentRequest.getContent());
        //TODO: 댓글 쓴 사용자만 수정 가능하게

        return "redirect:/posts/" + comment.getPost().getId();
    }

    @GetMapping("/delete/{commentId}")
    public String deleteComment(@PathVariable("commentId") Long commentId) {
        //TODO: 댓글 쓴 사용자, 게시글 작성자만 삭제 가능하게
        Long postId = commentService.getPost(commentId).getId();
        commentService.deleteComment(commentId);
        return "redirect:/posts/" + postId;
    }
}
