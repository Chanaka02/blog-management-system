package com.example.blog.controller;

import com.example.blog.dto.BookmarkResponse;
import com.example.blog.service.BookmarkService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users/{userId}/bookmarks")
public class BookmarkController {
    private final BookmarkService bookmarkService;

    public BookmarkController(BookmarkService bookmarkService) {
        this.bookmarkService = bookmarkService;
    }

    @PostMapping("/{postId}")
    public ResponseEntity<BookmarkResponse> addBookmark(@PathVariable Long userId, @PathVariable Long postId) {
        return new ResponseEntity<>(bookmarkService.addBookmark(userId, postId), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<BookmarkResponse>> getBookmarks(@PathVariable Long userId) {
        return ResponseEntity.ok(bookmarkService.getBookmarks(userId));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> removeBookmark(@PathVariable Long userId, @PathVariable Long postId) {
        bookmarkService.removeBookmark(userId, postId);
        return ResponseEntity.noContent().build();
    }
}