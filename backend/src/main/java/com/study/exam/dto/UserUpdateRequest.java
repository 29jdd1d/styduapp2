package com.study.exam.dto;

import lombok.Data;

@Data
public class UserUpdateRequest {
    private String nickname;
    private String phone;
    private String email;
    private Integer gender;
    private String targetUniversity;
    private String targetMajor;
    private Integer enrollmentYear;
}
