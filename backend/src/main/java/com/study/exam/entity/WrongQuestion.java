package com.study.exam.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "wrong_question")
public class WrongQuestion extends BaseEntity {

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "question_id", nullable = false)
    private Long questionId;

    @Column(name = "wrong_count")
    private Integer wrongCount = 1;

    @Column(name = "last_wrong_answer")
    private String lastWrongAnswer;

    @Column(name = "is_mastered")
    private Boolean isMastered = false;
}
