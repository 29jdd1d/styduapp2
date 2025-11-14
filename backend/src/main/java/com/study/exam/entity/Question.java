package com.study.exam.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "question")
public class Question extends BaseEntity {

    @Column(name = "category_id", nullable = false)
    private Long categoryId;

    @Column(name = "type")
    private String type; // SINGLE_CHOICE, MULTIPLE_CHOICE, TRUE_FALSE, FILL_BLANK, ESSAY

    @Column(name = "subject")
    private String subject; // POLITICS, ENGLISH, MATH, PROFESSIONAL

    @Column(name = "chapter")
    private String chapter;

    @Column(name = "knowledge_point")
    private String knowledgePoint;

    @Column(name = "year")
    private Integer year;

    @Column(name = "difficulty")
    private String difficulty; // EASY, MEDIUM, HARD

    @Column(name = "content", length = 2000, nullable = false)
    private String content;

    @Column(name = "options", length = 1000)
    private String options; // JSON format for choices

    @Column(name = "correct_answer", length = 500)
    private String correctAnswer;

    @Column(name = "explanation", length = 2000)
    private String explanation;

    @Column(name = "answer_count")
    private Integer answerCount = 0;

    @Column(name = "correct_count")
    private Integer correctCount = 0;

    @Column(name = "created_by")
    private Long createdBy;
}
