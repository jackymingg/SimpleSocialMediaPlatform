package com.socialmedia.platform.service;

import com.socialmedia.platform.dto.CommentRequest;
import com.socialmedia.platform.dto.CommentResponse;
import com.socialmedia.platform.model.Post;
import com.socialmedia.platform.model.User;
import com.socialmedia.platform.repository.CommentRepository;
import com.socialmedia.platform.repository.PostRepository;
import com.socialmedia.platform.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public CommentResponse addComment(Long postId, CommentRequest request) {
        String currentPhone = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByPhone(currentPhone)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        commentRepository.addComment(user.getUserId(), post.getPostId(), request.getContent());

        return CommentResponse.builder()
                .userId(user.getUserId())
                .userName(user.getUserName())
                .postId(post.getPostId())
                .content(request.getContent())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public List<CommentResponse> getCommentsByPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        return commentRepository.findByPost(post).stream()
                .map(comment -> CommentResponse.builder()
                        .commentId(comment.getCommentId())
                        .postId(comment.getPost().getPostId())
                        .userId(comment.getUser().getUserId())
                        .userName(comment.getUser().getUserName())
                        .content(comment.getContent())
                        .createdAt(comment.getCreatedAt())
                        .build()
                ).collect(Collectors.toList());
    }
}
