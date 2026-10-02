package com.example.blog.service;

import com.example.blog.dto.PostRequest;
import com.example.blog.dto.PostResponse;
import com.example.blog.entity.Post;
import com.example.blog.entity.Tag;
import com.example.blog.entity.User;
import com.example.blog.exception.ResourceNotFoundException;
import com.example.blog.repository.PostRepository;
import com.example.blog.repository.TagRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserService userService;
    private final TagRepository tagRepository;

    public PostService(PostRepository postRepository, UserService userService, TagRepository tagRepository) {
        this.postRepository = postRepository;
        this.userService = userService;
        this.tagRepository = tagRepository;
    }

    public PostResponse createPost(PostRequest request) {
        User user = userService.findUser(request.getUserId());
        Post post = new Post();
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setUser(user);
        post.setLikeCount(0);
        post.setTags(resolveTags(request.getTags()));
        return mapToResponse(postRepository.save(post));
    }

    public List<PostResponse> getAllPosts() {
        return postRepository.findAll().stream().map(this::mapToResponse).toList();
    }

    public List<PostResponse> getPostsByTag(String tagName) {
        return postRepository.findDistinctByTagsNameIgnoreCase(tagName).stream()
                .map(this::mapToResponse)
                .toList();
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
        post.setTags(resolveTags(request.getTags()));
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
                post.getUser().getName(),
                post.getTags().stream().map(Tag::getName).sorted().toList()
        );
    }

    private Set<Tag> resolveTags(List<String> tagNames) {
        if (tagNames == null) {
            return Set.of();
        }

        return tagNames.stream()
                .map(String::trim)
                .map(name -> name.toLowerCase(Locale.ROOT))
                .distinct()
                .map(name -> tagRepository.findByNameIgnoreCase(name)
                        .orElseGet(() -> tagRepository.save(new Tag(name))))
                .collect(Collectors.toSet());
    }
}
