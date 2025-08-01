package com.socialmedia.platform.service;

import com.socialmedia.platform.dto.PostRequest;
import com.socialmedia.platform.dto.PostResponse;
import com.socialmedia.platform.model.Post;
import com.socialmedia.platform.model.User;
import com.socialmedia.platform.repository.PostRepository;
import com.socialmedia.platform.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostResponse createPost(PostRequest request) {
        String currentEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByPhone(currentEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Post post = Post.builder()
                .content(request.getContent())
                .image(request.getImage())
                .user(user)
                .build();

        return toResponse(postRepository.save(post));
    }

    public List<PostResponse> getAllPosts() {
        return postRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public PostResponse updatePost(Long id, PostRequest request) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        String currentEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!post.getUser().getEmail().equals(currentEmail)) {
            throw new RuntimeException("You are not the author");
        }

        post.setContent(request.getContent());
        post.setImage(request.getImage());

        return toResponse(postRepository.save(post));
    }

    public void deletePost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        String currentEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!post.getUser().getEmail().equals(currentEmail)) {
            throw new RuntimeException("You are not the author");
        }

        postRepository.delete(post);
    }

    private PostResponse toResponse(Post post) {
        return PostResponse.builder()
                .postId(post.getPostId())
                .userId(post.getUser().getUserId())
                .userName(post.getUser().getUserName())
                .content(post.getContent())
                .image(post.getImage())
                .createdAt(post.getCreatedAt())
                .build();
    }
}



