package com.socialmedia.platform.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;

@Data
public class LoginRequest {
    @NotBlank
    private String phone;

    @NotBlank
    private String password;
}
