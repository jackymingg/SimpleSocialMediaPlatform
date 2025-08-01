package com.socialmedia.platform.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Data
public class RegisterRequest{

    @NotBlank
    @Pattern(regexp = "^[0-9]{10,15}$", message = "手機號碼格式錯誤")
    private String phone;

    @NotBlank
    private String password;

    @NotBlank
    private String email;

    @NotBlank
    private String userName;
}
