package com.study.exam.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "post")
public class Post extends BaseEntity {

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "type")
    private String type; // NEWS, EXPERIENCE, CHECK_IN

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", length = 5000, nullable = false)
    private String content;

    @Column(name = "images", length = 1000)
    private String images; // JSON array of image URLs

    @Column(name = "tags", length = 500)
    private String tags; // JSON array of tags

    @Column(name = "view_count")
    private Integer viewCount = 0;

    @Column(name = "like_count")
    private Integer likeCount = 0;

    @Column(name = "comment_count")
    private Integer commentCount = 0;

    @Column(name = "is_pinned")
    private Boolean isPinned = false;

    @Column(name = "status")
    private String status = "PUBLISHED"; // DRAFT, PUBLISHED, HIDDEN
}
