package com.study.exam.repository;

import com.study.exam.entity.LearningPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LearningPlanRepository extends JpaRepository<LearningPlan, Long> {
    
    List<LearningPlan> findByUserIdAndDeletedFalse(Long userId);
    
    List<LearningPlan> findByUserIdAndStatusAndDeletedFalse(Long userId, String status);
}
