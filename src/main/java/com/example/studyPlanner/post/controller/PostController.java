package com.example.studyPlanner.post.controller;


import com.example.studyPlanner.board.entity.Board;
import com.example.studyPlanner.board.service.BoardService;
import com.example.studyPlanner.planner.service.PlannerService;
import com.example.studyPlanner.post.dto.CreatePostReq;
import com.example.studyPlanner.post.dto.GetPostListRes;
import com.example.studyPlanner.post.dto.GetPostRes;
import com.example.studyPlanner.post.dto.ModifyPost;
import com.example.studyPlanner.post.entity.Post;
import com.example.studyPlanner.post.mapper.PostMapper;
import com.example.studyPlanner.post.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;
    private final BoardService boardService;

    // 네비게이션 바의 게시판 눌렀을 때, 메인 게시판 페이지(전체 게시글)
    @GetMapping("/all")
    public String getAllPosts(@RequestParam(defaultValue = "0", name = "page") int page, Model model) {
        //TODO: 게시판 이름 리스트도 넘겨야함
        List<Board> boards = boardService.getBoards();
        List<Post> posts = postService.getAllPosts(page);

        List<GetPostListRes> postListResList = posts.stream()
                .map(post -> PostMapper.INSTANCE.toListDTO(post))
                .collect(Collectors.toList());
        model.addAttribute("boards", boards);
        model.addAttribute("postListRes", postListResList);

        return "post/list-view";
    }

    //메인 게시판 페이지에서 카테고리 누르면 해당 게시판으로 이동
    @GetMapping("/board/{boardId}")
    public String getPosts(@PathVariable("boardId") Long id, @RequestParam(defaultValue = "0", name = "page") int page, Model model) {
        List<Post> posts = postService.getPosts(id, page);
        List<Board> boards = boardService.getBoards();

        List<GetPostListRes> postListResList = posts.stream()
                .map(post -> PostMapper.INSTANCE.toListDTO(post))
                .collect(Collectors.toList());

        model.addAttribute("boards", boards);
        model.addAttribute("postListRes", postListResList);

        return "post/list-view";
    }

    //게시물을 클릭하면 여기로
    @GetMapping("/{postId}")
    public String getPost(@PathVariable("postId") Long id, Model model) {
        Post post = postService.getPost(id);
        GetPostRes getPostRes = PostMapper.INSTANCE.toDTO(post);
        model.addAttribute("getPostRes", getPostRes);

        //게시물 내용 수정
        ModifyPost modifyPost = new ModifyPost();
        model.addAttribute("modifyPost", modifyPost);

        return "/post/detail-view";
    }

    //TODO: 검색하는 페이지 만들기(메인 게시판 페이지에서 돋보기 아이콘 누르면 넘어갈 수 있게)
    @GetMapping("/search")
    public List<GetPostListRes> searchPosts(@RequestParam("searchWord") String searchWord) {
        List<Post> posts = postService.searchPosts(searchWord);

        List<GetPostListRes> postListResList = posts.stream()
                .map(post -> PostMapper.INSTANCE.toListDTO(post))
                .collect(Collectors.toList());

        return postListResList;
    }

    @PostMapping()
    public String createPost(@ModelAttribute CreatePostReq postReq) {
        System.out.println("날짜: " + postReq.getCreatedAt());
        System.out.println("이름: " + postReq.getBoardName());
        System.out.println("내용: " + postReq.getContent());
        Long userId = 1L;
        Post post = postService.createPost(userId, postReq.getCreatedAt(), postReq.getBoardName(), postReq.getContent());
        return "redirect:/posts/" + post.getId();
    }


    //게시물 수정 페이지에서 입력 후 해당 API 호출
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
