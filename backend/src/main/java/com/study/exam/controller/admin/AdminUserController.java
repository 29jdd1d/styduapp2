package com.study.exam.controller.admin;

import com.study.exam.common.Result;
import com.study.exam.entity.User;
import com.study.exam.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/user")
@RequiredArgsConstructor
public class AdminUserController {

    private final UserRepository userRepository;

    @GetMapping("/list")
    public Result<Page<User>> getUserList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return Result.success(userRepository.findAll(PageRequest.of(page, size)));
    }

    @GetMapping("/{id}")
    public Result<User> getUser(@PathVariable Long id) {
        return userRepository.findById(id)
                .map(Result::success)
                .orElse(Result.error("User not found"));
    }

    @DeleteMapping("/{id}")
    public Result<Void> deleteUser(@PathVariable Long id) {
        try {
            userRepository.findById(id).ifPresent(user -> {
                user.setDeleted(true);
                userRepository.save(user);
            });
            return Result.success("User deleted successfully", null);
        } catch (Exception e) {
            return Result.error("Failed to delete user: " + e.getMessage());
        }
    }
}
