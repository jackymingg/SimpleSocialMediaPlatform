package com.socialmedia.platform.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class PostResponse {

    private Long postId;
    private Long userId;
    private String userName;
    private String content;
    private String image;
    private LocalDateTime createdAt;
}



