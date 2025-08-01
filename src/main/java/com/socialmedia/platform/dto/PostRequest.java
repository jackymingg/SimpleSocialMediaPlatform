package com.socialmedia.platform.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PostRequest {

    @NotBlank
    private String content;

    private String image; // 可選
}



