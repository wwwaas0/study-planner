package com.example.studyPlanner.post.controller;

//import com.example.studyPlanner.domain.post.dto.GetPostListRes;
//import com.example.studyPlanner.domain.post.dto.GetPostRes;
//import com.example.studyPlanner.domain.post.entity.Post;
////import com.example.studyPlanner.domain.post.mapper.PostMapper;
////import com.example.studyPlanner.domain.post.service.PostService;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.stream.Collectors;

//@Controller
//@Slf4j
//@RequiredArgsConstructor
//@RequestMapping("/posts")
//public class PostController {
//    private final PostService postService;
//
//    @GetMapping()
//    public List<GetPostListRes> getAllPosts(@RequestParam(defaultValue = "0", name = "page") int page) {
//        List<Post> posts = postService.getAllPosts(page);
//
//        List<GetPostListRes> postListResList = posts.stream()
//                .map(post -> PostMapper.INSTANCE.toListDTO(post))
//                .collect(Collectors.toList());
//
//        return postListResList;
//    }
//
//    @GetMapping("/{boardId}")
//    public List<GetPostListRes> getPosts(@PathVariable("boardId") Long id, @RequestParam(defaultValue = "0", name = "page") int page) {
//        List<Post> posts = postService.getPosts(id, page);
//
//        List<GetPostListRes> postListResList = posts.stream()
//                .map(post -> PostMapper.INSTANCE.toListDTO(post))
//                .collect(Collectors.toList());
//
//        return postListResList;
//    }
//
//    @GetMapping("/{postId}")
//    public GetPostRes getPost(@PathVariable("postId") Long id) {
//        Post post = postService.getPost(id);
//        GetPostRes getPostRes = PostMapper.INSTANCE.toDTO(post);
//
//        return getPostRes;
//    }
//
//    @GetMapping("/search")
//    public List<GetPostListRes> searchPosts(@RequestParam("searchWord") String searchWord) {
//        List<Post> posts = postService.searchPosts(searchWord);
//
//        List<GetPostListRes> postListResList = posts.stream()
//                .map(post -> PostMapper.INSTANCE.toListDTO(post))
//                .collect(Collectors.toList());
//
//        return postListResList;
//    }
//
//    //user id, board id, plannerId 다 path variable로 받을지, 그렇다면 순서는?
//    @PostMapping("/{plannerId}")
//    public String createPost(@PathVariable("plannerId") Long plannerId, @RequestBody CreatePostReq postReq) {
//        Post post = postService.createPost(plannerId, postReq.getUserId(), postReq.getBoardId(), postReq.getContent());
//        return plannerId + "번 게시글이 정상적으로 생성되었습니다.";
//    }
//
//    @PatchMapping("/{postId}")
//    public String updatePost(@PathVariable("postId") Long postId, @RequestBody String content) {
//        Post post = postService.updatePost(postId, content);
//        return postId + "번 게시글이 정상적으로 수정되었습니다.\n수정된 내용: " + content;
//    }
//
//    @DeleteMapping("{postId}")
//    public String deletePost(@PathVariable("postId") Long id) {
//        return postService.deletePost(id);
//    }
//}
