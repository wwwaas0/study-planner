package com.example.studyPlanner.post.controller;


import com.example.studyPlanner.post.dto.CreatePostReq;
import com.example.studyPlanner.post.dto.GetPostListRes;
import com.example.studyPlanner.post.dto.GetPostRes;
import com.example.studyPlanner.post.entity.Post;
import com.example.studyPlanner.post.mapper.PostMapper;
import com.example.studyPlanner.post.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;

    @GetMapping()
    public List<GetPostListRes> getAllPosts(@RequestParam(defaultValue = "0", name = "page") int page) {
        List<Post> posts = postService.getAllPosts(page);

        List<GetPostListRes> postListResList = posts.stream()
                .map(post -> PostMapper.INSTANCE.toListDTO(post))
                .collect(Collectors.toList());

        return postListResList;
    }

    @GetMapping("/{boardId}")
    public List<GetPostListRes> getPosts(@PathVariable("boardId") Long id, @RequestParam(defaultValue = "0", name = "page") int page) {
        List<Post> posts = postService.getPosts(id, page);

        List<GetPostListRes> postListResList = posts.stream()
                .map(post -> PostMapper.INSTANCE.toListDTO(post))
                .collect(Collectors.toList());

        return postListResList;
    }

    @GetMapping("/{postId}")
    public GetPostRes getPost(@PathVariable("postId") Long id) {
        Post post = postService.getPost(id);
        GetPostRes getPostRes = PostMapper.INSTANCE.toDTO(post);

        return getPostRes;
    }

    @GetMapping("/search")
    public List<GetPostListRes> searchPosts(@RequestParam("searchWord") String searchWord) {
        List<Post> posts = postService.searchPosts(searchWord);

        List<GetPostListRes> postListResList = posts.stream()
                .map(post -> PostMapper.INSTANCE.toListDTO(post))
                .collect(Collectors.toList());

        return postListResList;
    }

    //user id, board id, plannerId 다 path variable로 받을지, 그렇다면 순서는?
    @PostMapping()
    public String createPost(@ModelAttribute CreatePostReq postReq) {
        System.out.println("날짜: " + postReq.getCreatedAt());
        System.out.println("이름: " + postReq.getBoardName());
        System.out.println("내용: " + postReq.getContent());
        Long userId = 1L;
        Post post = postService.createPost(userId, postReq.getCreatedAt(), postReq.getBoardName(), postReq.getContent());
        return "redirect:/posts/" + post.getId();
    }

    @PatchMapping("/{postId}")
    public String updatePost(@PathVariable("postId") Long postId, @RequestBody String content) {
        Post post = postService.updatePost(postId, content);
        return postId + "번 게시글이 정상적으로 수정되었습니다.\n수정된 내용: " + content;
    }

    @DeleteMapping("{postId}")
    public String deletePost(@PathVariable("postId") Long id) {
        return postService.deletePost(id);
    }
}
