package com.study.exam.service;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import com.study.exam.dto.UserDashboardVO;
import com.study.exam.dto.UserUpdateRequest;
import com.study.exam.dto.WeChatLoginRequest;
import com.study.exam.entity.User;
import com.study.exam.repository.UserRepository;
import com.study.exam.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final WxMaService wxMaService;

    @Transactional
    public Map<String, Object> weChatLogin(WeChatLoginRequest request) {
        try {
            // Get openid from WeChat
            WxMaJscode2SessionResult session = wxMaService.getUserService()
                    .getSessionInfo(request.getCode());
            String openid = session.getOpenid();

            // Find or create user
            User user = userRepository.findByOpenid(openid)
                    .orElseGet(() -> {
                        User newUser = new User();
                        newUser.setOpenid(openid);
                        newUser.setNickname(request.getNickName());
                        newUser.setAvatar(request.getAvatarUrl());
                        newUser.setGender(request.getGender());
                        return userRepository.save(newUser);
                    });

            // Generate JWT token
            String token = jwtUtil.generateToken(user.getId(), user.getOpenid());

            Map<String, Object> result = new HashMap<>();
            result.put("token", token);
            result.put("user", user);

            return result;
        } catch (Exception e) {
            log.error("WeChat login failed", e);
            throw new RuntimeException("WeChat login failed: " + e.getMessage());
        }
    }

    public User getUserById(Long userId) {
        return userRepository.findByIdAndDeletedFalse(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Transactional
    public User updateUserInfo(Long userId, UserUpdateRequest request) {
        User user = getUserById(userId);
        
        if (request.getNickname() != null) {
            user.setNickname(request.getNickname());
        }
        if (request.getPhone() != null) {
            user.setPhone(request.getPhone());
        }
        if (request.getEmail() != null) {
            user.setEmail(request.getEmail());
        }
        if (request.getGender() != null) {
            user.setGender(request.getGender());
        }
        if (request.getTargetUniversity() != null) {
            user.setTargetUniversity(request.getTargetUniversity());
        }
        if (request.getTargetMajor() != null) {
            user.setTargetMajor(request.getTargetMajor());
        }
        if (request.getEnrollmentYear() != null) {
            user.setEnrollmentYear(request.getEnrollmentYear());
        }

        return userRepository.save(user);
    }

    public UserDashboardVO getUserDashboard(Long userId) {
        User user = getUserById(userId);
        
        UserDashboardVO dashboard = new UserDashboardVO();
        dashboard.setUserId(user.getId());
        dashboard.setNickname(user.getNickname());
        dashboard.setAvatar(user.getAvatar());
        dashboard.setTargetUniversity(user.getTargetUniversity());
        dashboard.setTargetMajor(user.getTargetMajor());
        dashboard.setStudyDays(user.getStudyDays());
        dashboard.setTotalStudyTime(user.getTotalStudyTime());
        dashboard.setTotalQuestionsAnswered(user.getTotalQuestionsAnswered());
        dashboard.setCorrectAnswers(user.getCorrectAnswers());
        
        // Calculate accuracy
        if (user.getTotalQuestionsAnswered() > 0) {
            double accuracy = (double) user.getCorrectAnswers() / user.getTotalQuestionsAnswered() * 100;
            dashboard.setAccuracy(Math.round(accuracy * 100.0) / 100.0);
        } else {
            dashboard.setAccuracy(0.0);
        }

        return dashboard;
    }
}
