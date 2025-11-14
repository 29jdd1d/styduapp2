package com.study.exam.repository;

import com.study.exam.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    
    List<Question> findBySubjectAndDeletedFalse(String subject);
    
    List<Question> findByChapterAndDeletedFalse(String chapter);
    
    List<Question> findByKnowledgePointAndDeletedFalse(String knowledgePoint);
    
    List<Question> findByYearAndDeletedFalse(Integer year);
}
