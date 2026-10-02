package com.example.blog.dto;

import java.time.LocalDateTime;

public class BookmarkResponse {
    private Long id;
    private Long userId;
    private Long postId;
    private String postTitle;
    private LocalDateTime createdAt;

    public BookmarkResponse() {}

    public BookmarkResponse(Long id, Long userId, Long postId, String postTitle, LocalDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.postId = postId;
        this.postTitle = postTitle;
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public Long getPostId() { return postId; }
    public void setPostId(Long postId) { this.postId = postId; }
    public String getPostTitle() { return postTitle; }
    public void setPostTitle(String postTitle) { this.postTitle = postTitle; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}