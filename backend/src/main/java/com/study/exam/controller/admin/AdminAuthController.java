package com.study.exam.controller.admin;

import com.study.exam.common.Result;
import com.study.exam.dto.AdminLoginRequest;
import com.study.exam.dto.AdminLoginResponse;
import com.study.exam.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/auth")
@RequiredArgsConstructor
public class AdminAuthController {

    private final AdminService adminService;

    @PostMapping("/login")
    public Result<AdminLoginResponse> login(@RequestBody AdminLoginRequest request) {
        try {
            AdminLoginResponse response = adminService.login(request);
            return Result.success("登录成功", response);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @PostMapping("/logout")
    public Result<Void> logout() {
        // JWT is stateless, so we just return success
        // Client should remove the token from storage
        return Result.success("登出成功", null);
    }
}
