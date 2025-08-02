package com.socialmedia.platform.service;

import java.util.Map;
import java.util.HashMap;
import com.socialmedia.platform.dto.LoginRequest;
import com.socialmedia.platform.dto.RegisterRequest;
import com.socialmedia.platform.repository.UserRepository;
import com.socialmedia.platform.security.JwtService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Transactional
    public String register(RegisterRequest request) {
        if (userRepository.existsByPhone(request.getPhone())) {
            throw new RuntimeException("該手機號碼已被註冊");
        }

        // 密碼加密，透過儲存過程註冊
        userRepository.registerUser(
                request.getPhone(),
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()),
                request.getUserName()
        );

        return jwtService.generateToken(request.getPhone());
    }

    public Map<String, String> login(LoginRequest request) {
        return userRepository.findByPhone(request.getPhone())
                .filter(user -> passwordEncoder.matches(request.getPassword(), user.getPassword()))
                .map(user -> {
                    Map<String, String> result = new HashMap<>();
                    result.put("token", jwtService.generateToken(user.getPhone()));
                    result.put("userName", user.getUserName());
                    return result;
                })
                .orElseThrow(() -> new RuntimeException("帳號或密碼錯誤"));
    }
}







