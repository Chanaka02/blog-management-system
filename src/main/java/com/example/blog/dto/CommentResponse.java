package com.example.blog.dto;

import java.time.LocalDateTime;

public class CommentResponse {
    private Long id;
    private String content;
    private LocalDateTime createdAt;
    private Long postId;

    public CommentResponse() {}
    public CommentResponse(Long id, String content, LocalDateTime createdAt, Long postId) {
        this.id = id;
        this.content = content;
        this.createdAt = createdAt;
        this.postId = postId;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public Long getPostId() { return postId; }
    public void setPostId(Long postId) { this.postId = postId; }
}
