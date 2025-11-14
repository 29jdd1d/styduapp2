package com.study.exam.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "comment")
public class Comment extends BaseEntity {

    @Column(name = "post_id", nullable = false)
    private Long postId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "content", length = 1000, nullable = false)
    private String content;

    @Column(name = "parent_id")
    private Long parentId; // for nested comments

    @Column(name = "like_count")
    private Integer likeCount = 0;
}
