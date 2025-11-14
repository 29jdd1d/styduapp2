package com.study.exam.controller.admin;

import com.study.exam.common.Result;
import com.study.exam.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/admin/statistics")
@RequiredArgsConstructor
public class AdminStatisticsController {

    private final UserRepository userRepository;
    private final LearningResourceRepository resourceRepository;
    private final QuestionRepository questionRepository;
    private final PostRepository postRepository;
    private final LearningPlanRepository planRepository;

    @GetMapping("/overview")
    public Result<Map<String, Object>> getOverviewStatistics() {
        Map<String, Object> stats = new HashMap<>();
        
        stats.put("totalUsers", userRepository.count());
        stats.put("totalResources", resourceRepository.count());
        stats.put("totalQuestions", questionRepository.count());
        stats.put("totalPosts", postRepository.count());
        stats.put("totalPlans", planRepository.count());
        
        return Result.success(stats);
    }
}
