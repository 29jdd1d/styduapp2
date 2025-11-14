package com.study.exam.dto;

import lombok.Data;

@Data
public class UserDashboardVO {
    private Long userId;
    private String nickname;
    private String avatar;
    private String targetUniversity;
    private String targetMajor;
    private Integer studyDays;
    private Long totalStudyTime; // in minutes
    private Integer totalQuestionsAnswered;
    private Integer correctAnswers;
    private Double accuracy; // accuracy rate
}
