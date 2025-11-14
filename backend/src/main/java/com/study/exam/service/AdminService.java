package com.study.exam.service;

import com.study.exam.dto.AdminLoginRequest;
import com.study.exam.dto.AdminLoginResponse;
import com.study.exam.entity.User;
import com.study.exam.repository.UserRepository;
import com.study.exam.utils.JwtUtil;
import com.study.exam.utils.Md5Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public AdminLoginResponse login(AdminLoginRequest request) {
        // Find user by username
        User user = userRepository.findByUsernameAndDeletedFalse(request.getUsername())
                .orElseThrow(() -> new RuntimeException("用户名或密码错误"));

        // Verify user is admin
        if (!"ADMIN".equals(user.getRole())) {
            throw new RuntimeException("无权限访问");
        }

        // Verify password
        String encryptedPassword = Md5Util.md5(request.getPassword());
        if (!encryptedPassword.equals(user.getPassword())) {
            throw new RuntimeException("用户名或密码错误");
        }

        // Generate JWT token
        String token = jwtUtil.generateToken(user.getId(), user.getUsername());

        return new AdminLoginResponse(
                token,
                user.getId(),
                user.getUsername(),
                user.getNickname(),
                user.getRole()
        );
    }
}
