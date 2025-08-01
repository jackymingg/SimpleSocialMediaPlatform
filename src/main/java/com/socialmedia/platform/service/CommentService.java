package com.socialmedia.platform.service;

import com.socialmedia.platform.dto.CommentRequest;
import com.socialmedia.platform.dto.CommentResponse;
import com.socialmedia.platform.model.Comment;
import com.socialmedia.platform.model.Post;
import com.socialmedia.platform.model.User;
import com.socialmedia.platform.repository.CommentRepository;
import com.socialmedia.platform.repository.PostRepository;
import com.socialmedia.platform.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public CommentResponse addComment(Long postId, CommentRequest request) {
        String currentEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByPhone(currentEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        Comment comment = Comment.builder()
                .content(request.getContent())
                .post(post)
                .user(user)
                .build();

        return toResponse(commentRepository.save(comment));
    }

    public List<CommentResponse> getCommentsByPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        return commentRepository.findByPost(post).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private CommentResponse toResponse(Comment comment) {
        return CommentResponse.builder()
                .commentId(comment.getCommentId())
                .postId(comment.getPost().getPostId())
                .userId(comment.getUser().getUserId())
                .userName(comment.getUser().getUserName())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .build();
    }
}



