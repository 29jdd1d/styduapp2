package com.study.exam.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "study_check_in")
public class StudyCheckIn extends BaseEntity {

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "check_in_date", nullable = false)
    private LocalDate checkInDate;

    @Column(name = "study_duration")
    private Integer studyDuration; // in minutes

    @Column(name = "tasks_completed")
    private Integer tasksCompleted;

    @Column(name = "note", length = 500)
    private String note;
}
