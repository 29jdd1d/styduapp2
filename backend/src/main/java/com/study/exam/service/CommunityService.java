package com.study.exam.service;

import com.study.exam.entity.Comment;
import com.study.exam.entity.Post;
import com.study.exam.repository.CommentRepository;
import com.study.exam.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommunityService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public Page<Post> getAllPosts(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    public List<Post> getPostsByType(String type) {
        return postRepository.findByTypeAndStatusAndDeletedFalse(type, "PUBLISHED");
    }

    public List<Post> getUserPosts(Long userId) {
        return postRepository.findByUserIdAndDeletedFalse(userId);
    }

    public Post getPostById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));
    }

    @Transactional
    public Post createPost(Post post) {
        post.setStatus("PUBLISHED");
        return postRepository.save(post);
    }

    @Transactional
    public Post updatePost(Long id, Post updatedPost) {
        Post post = getPostById(id);
        
        if (updatedPost.getTitle() != null) {
            post.setTitle(updatedPost.getTitle());
        }
        if (updatedPost.getContent() != null) {
            post.setContent(updatedPost.getContent());
        }
        if (updatedPost.getImages() != null) {
            post.setImages(updatedPost.getImages());
        }
        if (updatedPost.getTags() != null) {
            post.setTags(updatedPost.getTags());
        }

        return postRepository.save(post);
    }

    @Transactional
    public void deletePost(Long id) {
        Post post = getPostById(id);
        post.setDeleted(true);
        postRepository.save(post);
    }

    @Transactional
    public void incrementViewCount(Long postId) {
        Post post = getPostById(postId);
        post.setViewCount(post.getViewCount() + 1);
        postRepository.save(post);
    }

    @Transactional
    public void likePost(Long postId) {
        Post post = getPostById(postId);
        post.setLikeCount(post.getLikeCount() + 1);
        postRepository.save(post);
    }

    // Comment methods
    public List<Comment> getPostComments(Long postId) {
        return commentRepository.findByPostIdAndDeletedFalse(postId);
    }

    @Transactional
    public Comment createComment(Comment comment) {
        Comment saved = commentRepository.save(comment);
        
        // Increment post comment count
        postRepository.findById(comment.getPostId()).ifPresent(post -> {
            post.setCommentCount(post.getCommentCount() + 1);
            postRepository.save(post);
        });
        
        return saved;
    }

    @Transactional
    public void deleteComment(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
        
        comment.setDeleted(true);
        commentRepository.save(comment);
        
        // Decrement post comment count
        postRepository.findById(comment.getPostId()).ifPresent(post -> {
            post.setCommentCount(Math.max(0, post.getCommentCount() - 1));
            postRepository.save(post);
        });
    }

    @Transactional
    public void likeComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
        comment.setLikeCount(comment.getLikeCount() + 1);
        commentRepository.save(comment);
    }
}
