package com.study.exam.dto;

import lombok.Data;

@Data
public class WeChatLoginRequest {
    private String code;
    private String nickName;
    private String avatarUrl;
    private Integer gender;
}
