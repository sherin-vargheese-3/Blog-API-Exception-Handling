package com.exercise.exceptionblog.controller;

import com.exercise.exceptionblog.dtos.CommentDTO;
import com.exercise.exceptionblog.dtos.PostDTO;
import com.exercise.exceptionblog.service.BlogService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BlogController {

    private final BlogService blogService;

    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    @PostMapping("/posts")
    public PostDTO createPost(@Valid @RequestBody PostDTO postDTO) {
        return blogService.createPost(postDTO);
    }

    @GetMapping("/posts")
    public List<PostDTO> getAllPosts() {
        return blogService.getAllPosts();
    }

    @GetMapping("/posts/{id}")
    public PostDTO getById(@PathVariable Long id) {
        return blogService.getById(id);
    }

    @PostMapping("/posts/{postId}/comments")
    public CommentDTO createComment(
            @PathVariable Long postId,
            @Valid @RequestBody CommentDTO commentDTO) {
        return blogService.createComment(postId, commentDTO);
    }

    @GetMapping("/posts/{postId}/comments")
    public List<CommentDTO> getCommentsByPostId(@PathVariable Long postId) {
        return blogService.getCommentsByPostId(postId);
    }
}
