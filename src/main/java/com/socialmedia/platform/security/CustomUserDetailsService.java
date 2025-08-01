package com.socialmedia.platform.security;

import com.socialmedia.platform.model.User;
import com.socialmedia.platform.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
        User user = userRepository.findByPhone(phone)
                .orElseThrow(() -> new UsernameNotFoundException("找不到用戶：" + phone));

        return new org.springframework.security.core.userdetails.User(
                user.getPhone(),
                user.getPassword(),
                java.util.Collections.emptyList() // 無角色（可擴充）
        );
    }
}

