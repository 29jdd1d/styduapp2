package com.study.exam.controller;

import com.study.exam.common.Result;
import com.study.exam.dto.UserDashboardVO;
import com.study.exam.dto.UserUpdateRequest;
import com.study.exam.dto.WeChatLoginRequest;
import com.study.exam.entity.User;
import com.study.exam.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/login/wechat")
    public Result<Map<String, Object>> weChatLogin(@RequestBody WeChatLoginRequest request) {
        try {
            Map<String, Object> result = userService.weChatLogin(request);
            return Result.success(result);
        } catch (Exception e) {
            return Result.error("Login failed: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public Result<User> getUserInfo(@PathVariable Long id) {
        try {
            User user = userService.getUserById(id);
            return Result.success(user);
        } catch (Exception e) {
            return Result.error("Failed to get user info: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public Result<User> updateUserInfo(@PathVariable Long id, @RequestBody UserUpdateRequest request) {
        try {
            User user = userService.updateUserInfo(id, request);
            return Result.success("User info updated successfully", user);
        } catch (Exception e) {
            return Result.error("Failed to update user info: " + e.getMessage());
        }
    }

    @GetMapping("/{id}/dashboard")
    public Result<UserDashboardVO> getUserDashboard(@PathVariable Long id) {
        try {
            UserDashboardVO dashboard = userService.getUserDashboard(id);
            return Result.success(dashboard);
        } catch (Exception e) {
            return Result.error("Failed to get dashboard: " + e.getMessage());
        }
    }
}
