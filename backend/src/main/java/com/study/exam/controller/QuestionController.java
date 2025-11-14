package com.study.exam.controller;

import com.study.exam.common.Result;
import com.study.exam.entity.Question;
import com.study.exam.entity.WrongQuestion;
import com.study.exam.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/question")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping("/list")
    public Result<Page<Question>> getAllQuestions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return Result.success(questionService.getAllQuestions(PageRequest.of(page, size)));
    }

    @GetMapping("/subject/{subject}")
    public Result<List<Question>> getQuestionsBySubject(@PathVariable String subject) {
        return Result.success(questionService.getQuestionsBySubject(subject));
    }

    @GetMapping("/chapter/{chapter}")
    public Result<List<Question>> getQuestionsByChapter(@PathVariable String chapter) {
        return Result.success(questionService.getQuestionsByChapter(chapter));
    }

    @GetMapping("/year/{year}")
    public Result<List<Question>> getQuestionsByYear(@PathVariable Integer year) {
        return Result.success(questionService.getQuestionsByYear(year));
    }

    @GetMapping("/{id}")
    public Result<Question> getQuestionById(@PathVariable Long id) {
        try {
            return Result.success(questionService.getQuestionById(id));
        } catch (Exception e) {
            return Result.error("Failed to get question: " + e.getMessage());
        }
    }

    @PostMapping
    public Result<Question> createQuestion(@RequestBody Question question) {
        return Result.success(questionService.createQuestion(question));
    }

    @PutMapping("/{id}")
    public Result<Question> updateQuestion(@PathVariable Long id, @RequestBody Question question) {
        try {
            return Result.success(questionService.updateQuestion(id, question));
        } catch (Exception e) {
            return Result.error("Failed to update question: " + e.getMessage());
        }
    }

    @PostMapping("/submit")
    public Result<Void> submitAnswer(@RequestBody Map<String, Object> request) {
        try {
            Long userId = Long.valueOf(request.get("userId").toString());
            Long questionId = Long.valueOf(request.get("questionId").toString());
            String userAnswer = request.get("userAnswer").toString();
            
            questionService.submitAnswer(userId, questionId, userAnswer);
            return Result.success("Answer submitted successfully", null);
        } catch (Exception e) {
            return Result.error("Failed to submit answer: " + e.getMessage());
        }
    }

    @GetMapping("/wrong/{userId}")
    public Result<List<WrongQuestion>> getUserWrongQuestions(@PathVariable Long userId) {
        return Result.success(questionService.getUserWrongQuestions(userId));
    }

    @PutMapping("/mastered")
    public Result<Void> markAsMastered(
            @RequestParam Long userId,
            @RequestParam Long questionId) {
        try {
            questionService.markQuestionAsMastered(userId, questionId);
            return Result.success("Question marked as mastered", null);
        } catch (Exception e) {
            return Result.error("Failed to mark question: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public Result<Void> deleteQuestion(@PathVariable Long id) {
        try {
            questionService.deleteQuestion(id);
            return Result.success("Question deleted successfully", null);
        } catch (Exception e) {
            return Result.error("Failed to delete question: " + e.getMessage());
        }
    }
}
