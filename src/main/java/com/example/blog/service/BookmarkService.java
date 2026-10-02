package com.example.blog.service;

import com.example.blog.dto.BookmarkResponse;
import com.example.blog.entity.Bookmark;
import com.example.blog.entity.Post;
import com.example.blog.entity.User;
import com.example.blog.exception.ResourceNotFoundException;
import com.example.blog.repository.BookmarkRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookmarkService {
    private final BookmarkRepository bookmarkRepository;
    private final UserService userService;
    private final PostService postService;

    public BookmarkService(BookmarkRepository bookmarkRepository, UserService userService, PostService postService) {
        this.bookmarkRepository = bookmarkRepository;
        this.userService = userService;
        this.postService = postService;
    }

    public BookmarkResponse addBookmark(Long userId, Long postId) {
        User user = userService.findUser(userId);
        Post post = postService.findPost(postId);

        if (bookmarkRepository.findByUserIdAndPostId(userId, postId).isPresent()) {
            throw new IllegalStateException("Post is already bookmarked by this user");
        }

        Bookmark bookmark = new Bookmark();
        bookmark.setUser(user);
        bookmark.setPost(post);
        try {
            return mapToResponse(bookmarkRepository.save(bookmark));
        } catch (DataIntegrityViolationException ex) {
            throw new IllegalStateException("Post is already bookmarked by this user");
        }
    }

    public List<BookmarkResponse> getBookmarks(Long userId) {
        userService.findUser(userId);
        return bookmarkRepository.findByUserIdOrderByCreatedAtDesc(userId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public void removeBookmark(Long userId, Long postId) {
        Bookmark bookmark = bookmarkRepository.findByUserIdAndPostId(userId, postId)
                .orElseThrow(() -> new ResourceNotFoundException("Bookmark not found for this user and post"));
        bookmarkRepository.delete(bookmark);
    }

    private BookmarkResponse mapToResponse(Bookmark bookmark) {
        return new BookmarkResponse(
                bookmark.getId(),
                bookmark.getUser().getId(),
                bookmark.getPost().getId(),
                bookmark.getPost().getTitle(),
                bookmark.getCreatedAt()
        );
    }
}