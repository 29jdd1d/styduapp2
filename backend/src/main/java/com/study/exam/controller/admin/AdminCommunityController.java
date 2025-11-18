package com.study.exam.controller.admin;

import com.study.exam.common.Result;
import com.study.exam.entity.Comment;
import com.study.exam.entity.Post;
import com.study.exam.service.CommunityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/community")
@RequiredArgsConstructor
public class AdminCommunityController {

    private final CommunityService communityService;

    @GetMapping("/post/list")
    public Result<Page<Post>> getAllPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return Result.success(communityService.getAllPosts(PageRequest.of(page, size)));
    }

    @GetMapping("/post/{id}")
    public Result<Post> getPost(@PathVariable Long id) {
        try {
            return Result.success(communityService.getPostById(id));
        } catch (Exception e) {
            return Result.error("Failed to get post: " + e.getMessage());
        }
    }

    @DeleteMapping("/post/{id}")
    public Result<Void> deletePost(@PathVariable Long id) {
        try {
            communityService.deletePost(id);
            return Result.success("Post deleted successfully", null);
        } catch (Exception e) {
            return Result.error("Failed to delete post: " + e.getMessage());
        }
    }

    @GetMapping("/post/{postId}/comments")
    public Result<List<Comment>> getPostComments(@PathVariable Long postId) {
        return Result.success(communityService.getPostComments(postId));
    }

    @DeleteMapping("/comment/{id}")
    public Result<Void> deleteComment(@PathVariable Long id) {
        try {
            communityService.deleteComment(id);
            return Result.success("Comment deleted successfully", null);
        } catch (Exception e) {
            return Result.error("Failed to delete comment: " + e.getMessage());
        }
    }
}
