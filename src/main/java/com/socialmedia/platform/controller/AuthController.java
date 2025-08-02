package com.socialmedia.platform.controller;

import java.util.Map;
import com.socialmedia.platform.dto.LoginRequest;
import com.socialmedia.platform.dto.RegisterRequest;
import com.socialmedia.platform.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@Validated @RequestBody RegisterRequest request) {
        String token = authService.register(request);
        return ResponseEntity.ok().body(token);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@Validated @RequestBody LoginRequest request) {
        Map<String, String> result = authService.login(request);
        return ResponseEntity.ok(result);
    }




}



