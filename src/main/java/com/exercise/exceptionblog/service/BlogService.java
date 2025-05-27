package com.exercise.exceptionblog.service;

import com.exercise.exceptionblog.dtos.CommentDTO;
import com.exercise.exceptionblog.dtos.PostDTO;
import com.exercise.exceptionblog.exception.ResourceNotFoundException;
import com.exercise.exceptionblog.exception.UnauthorizedException;
import com.exercise.exceptionblog.model.Comment;
import com.exercise.exceptionblog.model.Post;
import com.exercise.exceptionblog.repository.CommentRepository;
import com.exercise.exceptionblog.repository.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class BlogService {

    @Autowired
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public BlogService(PostRepository postRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    public PostDTO createPost(PostDTO postDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));

        if (!isAdmin) {
            throw new UnauthorizedException("User is not authorized to perform this action. ADMIN role required.");
        }

        Post post = new Post();
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        Post savedPost = postRepository.save(post);
        return convertToPostDto(savedPost);
    }

    public List<PostDTO> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        return posts.stream()
                .map(this::convertToPostDto)
                .toList();
    }

    public PostDTO getById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + id));
        return convertToPostDto(post);
    }

    public CommentDTO createComment(Long postId, CommentDTO commentDTO) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + postId));

        Comment comment = new Comment();
        comment.setContent(commentDTO.getContent());
        comment.setPost(post);

        Comment savedComment = commentRepository.save(comment);
        return convertToCommentDto(savedComment);
    }

    public List<CommentDTO> getCommentsByPostId(Long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
        return comments.stream()
                .map(this::convertToCommentDto)
                .toList();
    }

    public PostDTO convertToPostDto(Post post) {
        return PostDTO.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .build();
    }

    public CommentDTO convertToCommentDto(Comment comment) {
        return CommentDTO.builder()
                .content(comment.getContent())
                .build();
    }
}
