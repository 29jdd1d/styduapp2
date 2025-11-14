package com.study.exam.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "learning_resource")
public class LearningResource extends BaseEntity {

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "category_id", nullable = false)
    private Long categoryId;

    @Column(name = "type")
    private String type; // VIDEO, DOCUMENT, QUESTION_BANK

    @Column(name = "file_url")
    private String fileUrl;

    @Column(name = "cover_image")
    private String coverImage;

    @Column(name = "file_size")
    private Long fileSize; // in bytes

    @Column(name = "duration")
    private Integer duration; // for videos, in seconds

    @Column(name = "view_count")
    private Integer viewCount = 0;

    @Column(name = "download_count")
    private Integer downloadCount = 0;

    @Column(name = "status")
    private String status = "DRAFT"; // DRAFT, PUBLISHED, ARCHIVED

    @Column(name = "created_by")
    private Long createdBy;
}
