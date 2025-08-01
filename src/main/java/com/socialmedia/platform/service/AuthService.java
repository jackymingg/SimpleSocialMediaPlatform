package com.socialmedia.platform.service;

import com.socialmedia.platform.dto.LoginRequest;
import com.socialmedia.platform.dto.RegisterRequest;
import com.socialmedia.platform.model.User;
import com.socialmedia.platform.repository.UserRepository;
import com.socialmedia.platform.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public String register(RegisterRequest request) {
        if (userRepository.existsByPhone(request.getPhone())) {
            throw new RuntimeException("該手機號碼已被註冊");
        }

        User user = User.builder()
                .phone(request.getPhone())
                .email(request.getEmail())
                .userName(request.getUserName())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        userRepository.save(user);
        return jwtService.generateToken(user.getPhone());
    }

    public String login(LoginRequest request) {
        User user = userRepository.findByPhone(request.getPhone())
                .orElseThrow(() -> new RuntimeException("查無此用戶"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("密碼錯誤");
        }

        return jwtService.generateToken(user.getPhone());
    }
}



