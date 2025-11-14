package com.study.exam.repository;

import com.study.exam.entity.ResourceCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResourceCategoryRepository extends JpaRepository<ResourceCategory, Long> {
    
    List<ResourceCategory> findByParentIdAndDeletedFalse(Long parentId);
    
    List<ResourceCategory> findByTypeAndDeletedFalse(String type);
}
