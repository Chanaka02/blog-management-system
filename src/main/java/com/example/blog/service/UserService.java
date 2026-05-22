package com.example.blog.service;

import com.example.blog.dto.UserRequest;
import com.example.blog.dto.UserResponse;
import com.example.blog.entity.User;
import com.example.blog.exception.ResourceNotFoundException;
import com.example.blog.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponse createUser(UserRequest request) {
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        return mapToResponse(userRepository.save(user));
    }

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream().map(this::mapToResponse).toList();
    }

    public UserResponse getUserById(Long id) {
        return mapToResponse(findUser(id));
    }

    public UserResponse updateUser(Long id, UserRequest request) {
        User user = findUser(id);
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        return mapToResponse(userRepository.save(user));
    }

    public void deleteUser(Long id) {
        User user = findUser(id);
        userRepository.delete(user);
    }

    public User findUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }

    private UserResponse mapToResponse(User user) {
        return new UserResponse(user.getId(), user.getName(), user.getEmail());
    }
}
