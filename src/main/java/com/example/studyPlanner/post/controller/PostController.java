package com.example.studyPlanner.post.controller;


import com.example.studyPlanner.board.entity.Board;
import com.example.studyPlanner.board.service.BoardService;
import com.example.studyPlanner.comment.dto.CreateCommentRequest;
import com.example.studyPlanner.comment.dto.GetCommentListRes;
import com.example.studyPlanner.comment.dto.UpdateCommentRequest;
import com.example.studyPlanner.comment.service.CommentService;
import com.example.studyPlanner.post.dto.CreatePostReq;
import com.example.studyPlanner.post.dto.GetPostListRes;
import com.example.studyPlanner.post.dto.GetPostRes;
import com.example.studyPlanner.post.dto.ModifyPost;
import com.example.studyPlanner.post.entity.Post;
import com.example.studyPlanner.post.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;
    private final BoardService boardService;
    private final CommentService commentService;

    @GetMapping("/all")
    public String getAllPosts(@RequestParam(defaultValue = "0", name = "page") int page, Model model) {
        List<Board> boards = boardService.getBoards();
        List<GetPostListRes> getPostListRes = postService.getAllPosts(page);

        model.addAttribute("boards", boards);
        model.addAttribute("postListRes", getPostListRes);

        return "post/list-view";
    }

    @GetMapping("/board/{boardId}")
    public String getPosts(@PathVariable("boardId") Long id, @RequestParam(defaultValue = "0", name = "page") int page, Model model) {
        List<GetPostListRes> getPostListRes = postService.getPosts(id, page);
        List<Board> boards = boardService.getBoards();

        model.addAttribute("boards", boards);
        model.addAttribute("postListRes", getPostListRes);

        return "post/list-view";
    }

    @GetMapping("/{postId}")
    public String getPost(@PathVariable("postId") Long id, Model model) {
        GetPostRes getPostRes = postService.getPost(id);
        model.addAttribute("getPostRes", getPostRes);

        //게시물 내용 수정 폼
        ModifyPost modifyPost = new ModifyPost();
        model.addAttribute("modifyPost", modifyPost);

        //댓글 정보
        List<GetCommentListRes> getCommentListRes = commentService.getComments(id);
        model.addAttribute("getCommentListRes", getCommentListRes);

        //댓글 생성 폼
        CreateCommentRequest createCommentRequest = new CreateCommentRequest();
        model.addAttribute("createCommentRequest", createCommentRequest);

        //댓글 내용 수정 폼
        UpdateCommentRequest updateCommentRequest = new UpdateCommentRequest();
        model.addAttribute("updateCommentRequest", updateCommentRequest);

        return "/post/detail-view";
    }

    //TODO: 검색하는 페이지 만들기(메인 게시판 페이지에서 돋보기 아이콘 누르면 넘어갈 수 있게)
    @GetMapping("/search")
    public String searchPosts(@RequestParam(defaultValue = "0", name = "page") int page, @RequestParam("searchWord") String searchWord, Model model) {
        List<GetPostListRes> getPostListRes = postService.searchPosts(searchWord, page);
        model.addAttribute("postListRes", getPostListRes);

        return "post/search";
    }

    @PostMapping()
    public String createPost(@ModelAttribute CreatePostReq postReq) {
        Long userId = 1L;
        Post post = postService.createPost(userId, postReq.getCreatedAt(), postReq.getBoardName(), postReq.getContent());
        return "redirect:/posts/" + post.getId();
    }

    @PostMapping("/{postId}")
    public String updatePost(@PathVariable("postId") Long postId, @ModelAttribute ModifyPost modifyPost) {
        Post post = postService.updatePost(postId, modifyPost.getNewName());
        return "redirect:/posts/" + post.getId();
    }

    @GetMapping("/delete/{postId}")
    public String deletePost(@PathVariable("postId") Long id) {
        postService.deletePost(id);

        return "redirect:/posts/all";
    }
}
