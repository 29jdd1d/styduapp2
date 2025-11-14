package com.study.exam.repository;

import com.study.exam.entity.PlanTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlanTaskRepository extends JpaRepository<PlanTask, Long> {
    
    List<PlanTask> findByPlanIdAndDeletedFalse(Long planId);
    
    List<PlanTask> findByPlanIdAndStatusAndDeletedFalse(Long planId, String status);
}
