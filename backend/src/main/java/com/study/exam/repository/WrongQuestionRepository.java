package com.study.exam.repository;

import com.study.exam.entity.WrongQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WrongQuestionRepository extends JpaRepository<WrongQuestion, Long> {
    
    List<WrongQuestion> findByUserIdAndDeletedFalse(Long userId);
    
    List<WrongQuestion> findByUserIdAndIsMasteredFalseAndDeletedFalse(Long userId);
    
    Optional<WrongQuestion> findByUserIdAndQuestionIdAndDeletedFalse(Long userId, Long questionId);
}
