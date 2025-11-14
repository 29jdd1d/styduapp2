package com.study.exam.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "plan_task")
public class PlanTask extends BaseEntity {

    @Column(name = "plan_id", nullable = false)
    private Long planId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "category")
    private String category; // POLITICS, ENGLISH, MATH, PROFESSIONAL

    @Column(name = "resource_id")
    private Long resourceId;

    @Column(name = "scheduled_date")
    private LocalDate scheduledDate;

    @Column(name = "completed_date")
    private LocalDate completedDate;

    @Column(name = "status")
    private String status = "PENDING"; // PENDING, IN_PROGRESS, COMPLETED

    @Column(name = "estimated_time")
    private Integer estimatedTime; // in minutes

    @Column(name = "actual_time")
    private Integer actualTime; // in minutes
}
