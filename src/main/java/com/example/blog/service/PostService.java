package com.example.blog.service;

import com.example.blog.dto.PostRequest;
import com.example.blog.dto.PostResponse;
import com.example.blog.entity.Post;
import com.example.blog.entity.User;
import com.example.blog.exception.ResourceNotFoundException;
import com.example.blog.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserService userService;

    public PostService(PostRepository postRepository, UserService userService) {
        this.postRepository = postRepository;
        this.userService = userService;
    }

    public PostResponse createPost(PostRequest request) {
        User user = userService.findUser(request.getUserId());
        Post post = new Post();
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setUser(user);
        post.setLikeCount(0);
        return mapToResponse(postRepository.save(post));
    }

    public List<PostResponse> getAllPosts() {
        return postRepository.findAll().stream().map(this::mapToResponse).toList();
    }

    public PostResponse getPostById(Long id) {
        return mapToResponse(findPost(id));
    }

    public PostResponse updatePost(Long id, PostRequest request) {
        Post post = findPost(id);
        User user = userService.findUser(request.getUserId());
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setUser(user);
        return mapToResponse(postRepository.save(post));
    }

    public void deletePost(Long id) {
        Post post = findPost(id);
        postRepository.delete(post);
    }

    public PostResponse likePost(Long id) {
        Post post = findPost(id);
        post.setLikeCount(post.getLikeCount() + 1);
        return mapToResponse(postRepository.save(post));
    }

    public Post findPost(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + id));
    }

    private PostResponse mapToResponse(Post post) {
        return new PostResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getLikeCount(),
                post.getCreatedAt(),
                post.getUser().getId(),
                post.getUser().getName()
        );
    }
}
