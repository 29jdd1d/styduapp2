package com.study.exam.repository;

import com.study.exam.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    
    List<Comment> findByPostIdAndDeletedFalse(Long postId);
    
    List<Comment> findByParentIdAndDeletedFalse(Long parentId);
}
