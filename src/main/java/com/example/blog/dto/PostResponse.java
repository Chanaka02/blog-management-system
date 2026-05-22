package com.example.blog.dto;

import java.time.LocalDateTime;

public class PostResponse {
    private Long id;
    private String title;
    private String content;
    private int likeCount;
    private LocalDateTime createdAt;
    private Long userId;
    private String authorName;

    public PostResponse() {}
    public PostResponse(Long id, String title, String content, int likeCount, LocalDateTime createdAt, Long userId, String authorName) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.likeCount = likeCount;
        this.createdAt = createdAt;
        this.userId = userId;
        this.authorName = authorName;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public int getLikeCount() { return likeCount; }
    public void setLikeCount(int likeCount) { this.likeCount = likeCount; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public String getAuthorName() { return authorName; }
    public void setAuthorName(String authorName) { this.authorName = authorName; }
}
