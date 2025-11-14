package com.study.exam.controller;

import com.study.exam.common.Result;
import com.study.exam.entity.LearningPlan;
import com.study.exam.entity.PlanTask;
import com.study.exam.service.LearningPlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/plan")
@RequiredArgsConstructor
public class LearningPlanController {

    private final LearningPlanService planService;

    @GetMapping("/list")
    public Result<List<LearningPlan>> getAllPlans() {
        return Result.success(planService.getAllPlans());
    }

    @GetMapping("/user/{userId}")
    public Result<List<LearningPlan>> getUserPlans(@PathVariable Long userId) {
        return Result.success(planService.getUserPlans(userId));
    }

    @GetMapping("/{id}")
    public Result<LearningPlan> getPlanById(@PathVariable Long id) {
        try {
            return Result.success(planService.getPlanById(id));
        } catch (Exception e) {
            return Result.error("Failed to get plan: " + e.getMessage());
        }
    }

    @PostMapping
    public Result<LearningPlan> createPlan(@RequestBody LearningPlan plan) {
        return Result.success(planService.createPlan(plan));
    }

    @PostMapping("/generate")
    public Result<LearningPlan> generatePlan(
            @RequestParam Long userId,
            @RequestParam String targetUniversity,
            @RequestParam String targetMajor,
            @RequestParam String startDate,
            @RequestParam String endDate) {
        try {
            LearningPlan plan = planService.generatePlan(
                    userId,
                    targetUniversity,
                    targetMajor,
                    LocalDate.parse(startDate),
                    LocalDate.parse(endDate)
            );
            return Result.success("Plan generated successfully", plan);
        } catch (Exception e) {
            return Result.error("Failed to generate plan: " + e.getMessage());
        }
    }

    @GetMapping("/{planId}/tasks")
    public Result<List<PlanTask>> getPlanTasks(@PathVariable Long planId) {
        return Result.success(planService.getPlanTasks(planId));
    }

    @PutMapping("/task/{taskId}/complete")
    public Result<PlanTask> completeTask(
            @PathVariable Long taskId,
            @RequestParam Integer actualTime) {
        try {
            PlanTask task = planService.completeTask(taskId, actualTime);
            return Result.success("Task completed", task);
        } catch (Exception e) {
            return Result.error("Failed to complete task: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public Result<Void> deletePlan(@PathVariable Long id) {
        try {
            planService.deletePlan(id);
            return Result.success("Plan deleted successfully", null);
        } catch (Exception e) {
            return Result.error("Failed to delete plan: " + e.getMessage());
        }
    }
}
