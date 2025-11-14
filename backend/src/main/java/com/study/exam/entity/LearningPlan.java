package com.study.exam.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "learning_plan")
public class LearningPlan extends BaseEntity {

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "target_university")
    private String targetUniversity;

    @Column(name = "target_major")
    private String targetMajor;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "status")
    private String status = "ACTIVE"; // ACTIVE, COMPLETED, PAUSED

    @Column(name = "total_tasks")
    private Integer totalTasks = 0;

    @Column(name = "completed_tasks")
    private Integer completedTasks = 0;

    @Column(name = "progress")
    private Double progress = 0.0;
}
