package com.study.exam.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "user")
public class User extends BaseEntity {

    @Column(name = "openid", unique = true, nullable = false)
    private String openid;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "gender")
    private Integer gender; // 0: unknown, 1: male, 2: female

    @Column(name = "target_university")
    private String targetUniversity;

    @Column(name = "target_major")
    private String targetMajor;

    @Column(name = "enrollment_year")
    private Integer enrollmentYear;

    @Column(name = "study_days", nullable = false)
    private Integer studyDays = 0;

    @Column(name = "total_study_time", nullable = false)
    private Long totalStudyTime = 0L; // in minutes

    @Column(name = "total_questions_answered", nullable = false)
    private Integer totalQuestionsAnswered = 0;

    @Column(name = "correct_answers", nullable = false)
    private Integer correctAnswers = 0;

    @Column(name = "role")
    private String role = "USER"; // USER, ADMIN
}
