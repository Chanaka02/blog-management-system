package com.example.blog.service;

import com.example.blog.dto.CommentRequest;
import com.example.blog.dto.CommentResponse;
import com.example.blog.entity.Comment;
import com.example.blog.entity.Post;
import com.example.blog.exception.ResourceNotFoundException;
import com.example.blog.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostService postService;

    public CommentService(CommentRepository commentRepository, PostService postService) {
        this.commentRepository = commentRepository;
        this.postService = postService;
    }

    public CommentResponse addCommentToPost(Long postId, CommentRequest request) {
        Post post = postService.findPost(postId);
        Comment comment = new Comment();
        comment.setContent(request.getContent());
        comment.setPost(post);
        return mapToResponse(commentRepository.save(comment));
    }

    public List<CommentResponse> getCommentsByPost(Long postId) {
        postService.findPost(postId);
        return commentRepository.findByPostId(postId).stream().map(this::mapToResponse).toList();
    }

    public CommentResponse updateComment(Long commentId, CommentRequest request) {
        Comment comment = findComment(commentId);
        comment.setContent(request.getContent());
        return mapToResponse(commentRepository.save(comment));
    }

    public void deleteComment(Long commentId) {
        Comment comment = findComment(commentId);
        commentRepository.delete(comment);
    }

    private Comment findComment(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found with id: " + id));
    }

    private CommentResponse mapToResponse(Comment comment) {
        return new CommentResponse(
                comment.getId(),
                comment.getContent(),
                comment.getCreatedAt(),
                comment.getPost().getId()
        );
    }
}
