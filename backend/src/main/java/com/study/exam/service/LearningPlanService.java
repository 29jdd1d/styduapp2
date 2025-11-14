package com.study.exam.service;

import com.study.exam.entity.LearningPlan;
import com.study.exam.entity.PlanTask;
import com.study.exam.repository.LearningPlanRepository;
import com.study.exam.repository.PlanTaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class LearningPlanService {

    private final LearningPlanRepository planRepository;
    private final PlanTaskRepository taskRepository;

    public List<LearningPlan> getAllPlans() {
        return planRepository.findAll();
    }

    public List<LearningPlan> getUserPlans(Long userId) {
        return planRepository.findByUserIdAndDeletedFalse(userId);
    }

    public LearningPlan getPlanById(Long id) {
        return planRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Learning plan not found"));
    }

    @Transactional
    public LearningPlan createPlan(LearningPlan plan) {
        return planRepository.save(plan);
    }

    @Transactional
    public LearningPlan generatePlan(Long userId, String targetUniversity, String targetMajor, 
                                     LocalDate startDate, LocalDate endDate) {
        LearningPlan plan = new LearningPlan();
        plan.setUserId(userId);
        plan.setTitle("考研学习计划 - " + targetUniversity + " " + targetMajor);
        plan.setTargetUniversity(targetUniversity);
        plan.setTargetMajor(targetMajor);
        plan.setStartDate(startDate);
        plan.setEndDate(endDate);
        plan.setStatus("ACTIVE");
        
        plan = planRepository.save(plan);

        // Generate tasks based on the date range
        List<PlanTask> tasks = generateTasksForPlan(plan.getId(), startDate, endDate);
        plan.setTotalTasks(tasks.size());
        
        return planRepository.save(plan);
    }

    private List<PlanTask> generateTasksForPlan(Long planId, LocalDate startDate, LocalDate endDate) {
        List<PlanTask> tasks = new ArrayList<>();
        long daysBetween = ChronoUnit.DAYS.between(startDate, endDate);
        
        // Generate sample tasks for different subjects
        String[] subjects = {"POLITICS", "ENGLISH", "MATH", "PROFESSIONAL"};
        String[] taskTemplates = {"复习章节", "练习题目", "视频学习", "知识点总结"};
        
        LocalDate currentDate = startDate;
        int taskIndex = 0;
        
        while (!currentDate.isAfter(endDate) && taskIndex < 100) {
            String subject = subjects[taskIndex % subjects.length];
            String taskType = taskTemplates[taskIndex % taskTemplates.length];
            
            PlanTask task = new PlanTask();
            task.setPlanId(planId);
            task.setTitle(subject + " - " + taskType);
            task.setCategory(subject);
            task.setScheduledDate(currentDate);
            task.setEstimatedTime(120); // 2 hours
            task.setStatus("PENDING");
            
            tasks.add(taskRepository.save(task));
            
            currentDate = currentDate.plusDays(2);
            taskIndex++;
        }
        
        return tasks;
    }

    public List<PlanTask> getPlanTasks(Long planId) {
        return taskRepository.findByPlanIdAndDeletedFalse(planId);
    }

    @Transactional
    public PlanTask completeTask(Long taskId, Integer actualTime) {
        PlanTask task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        
        task.setStatus("COMPLETED");
        task.setCompletedDate(LocalDate.now());
        task.setActualTime(actualTime);
        
        task = taskRepository.save(task);

        // Update plan progress
        updatePlanProgress(task.getPlanId());
        
        return task;
    }

    @Transactional
    public void updatePlanProgress(Long planId) {
        LearningPlan plan = getPlanById(planId);
        List<PlanTask> tasks = taskRepository.findByPlanIdAndDeletedFalse(planId);
        
        long completedCount = tasks.stream()
                .filter(t -> "COMPLETED".equals(t.getStatus()))
                .count();
        
        plan.setCompletedTasks((int) completedCount);
        plan.setTotalTasks(tasks.size());
        
        if (tasks.size() > 0) {
            plan.setProgress((double) completedCount / tasks.size() * 100);
        }
        
        planRepository.save(plan);
    }

    @Transactional
    public void deletePlan(Long id) {
        LearningPlan plan = getPlanById(id);
        plan.setDeleted(true);
        planRepository.save(plan);
    }
}
