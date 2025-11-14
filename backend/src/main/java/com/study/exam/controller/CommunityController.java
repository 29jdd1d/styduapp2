package com.study.exam.controller;

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
@RequestMapping("/community")
@RequiredArgsConstructor
public class CommunityController {

    private final CommunityService communityService;

    @GetMapping("/post/list")
    public Result<Page<Post>> getAllPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return Result.success(communityService.getAllPosts(PageRequest.of(page, size)));
    }

    @GetMapping("/post/type/{type}")
    public Result<List<Post>> getPostsByType(@PathVariable String type) {
        return Result.success(communityService.getPostsByType(type));
    }

    @GetMapping("/post/user/{userId}")
    public Result<List<Post>> getUserPosts(@PathVariable Long userId) {
        return Result.success(communityService.getUserPosts(userId));
    }

    @GetMapping("/post/{id}")
    public Result<Post> getPostById(@PathVariable Long id) {
        try {
            communityService.incrementViewCount(id);
            return Result.success(communityService.getPostById(id));
        } catch (Exception e) {
            return Result.error("Failed to get post: " + e.getMessage());
        }
    }

    @PostMapping("/post")
    public Result<Post> createPost(@RequestBody Post post) {
        return Result.success(communityService.createPost(post));
    }

    @PutMapping("/post/{id}")
    public Result<Post> updatePost(@PathVariable Long id, @RequestBody Post post) {
        try {
            return Result.success(communityService.updatePost(id, post));
        } catch (Exception e) {
            return Result.error("Failed to update post: " + e.getMessage());
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

    @PostMapping("/post/{id}/like")
    public Result<Void> likePost(@PathVariable Long id) {
        try {
            communityService.likePost(id);
            return Result.success("Post liked", null);
        } catch (Exception e) {
            return Result.error("Failed to like post: " + e.getMessage());
        }
    }

    @GetMapping("/post/{postId}/comments")
    public Result<List<Comment>> getPostComments(@PathVariable Long postId) {
        return Result.success(communityService.getPostComments(postId));
    }

    @PostMapping("/comment")
    public Result<Comment> createComment(@RequestBody Comment comment) {
        return Result.success(communityService.createComment(comment));
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

    @PostMapping("/comment/{id}/like")
    public Result<Void> likeComment(@PathVariable Long id) {
        try {
            communityService.likeComment(id);
            return Result.success("Comment liked", null);
        } catch (Exception e) {
            return Result.error("Failed to like comment: " + e.getMessage());
        }
    }
}
