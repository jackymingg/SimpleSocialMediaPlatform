package com.socialmedia.platform.controller;

import com.socialmedia.platform.dto.CommentRequest;
import com.socialmedia.platform.dto.CommentResponse;
import com.socialmedia.platform.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{postId}")
    public ResponseEntity<CommentResponse> addComment(@PathVariable("postId") Long postId,
                                                      @Valid @RequestBody CommentRequest request) {

        return ResponseEntity.ok(commentService.addComment(postId, request));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<List<CommentResponse>> getComments(@PathVariable("postId") Long postId) {

        return ResponseEntity.ok(commentService.getCommentsByPost(postId));
    }
}



