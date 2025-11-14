package com.study.exam.repository;

import com.study.exam.entity.LearningResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LearningResourceRepository extends JpaRepository<LearningResource, Long> {
    
    List<LearningResource> findByCategoryIdAndDeletedFalse(Long categoryId);
    
    List<LearningResource> findByStatusAndDeletedFalse(String status);
    
    List<LearningResource> findByTypeAndStatusAndDeletedFalse(String type, String status);
}
