package com.study.exam.service;

import com.study.exam.entity.LearningResource;
import com.study.exam.entity.ResourceCategory;
import com.study.exam.repository.LearningResourceRepository;
import com.study.exam.repository.ResourceCategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class LearningResourceService {

    private final LearningResourceRepository resourceRepository;
    private final ResourceCategoryRepository categoryRepository;
    private final FileService fileService;

    public List<ResourceCategory> getAllCategories() {
        return categoryRepository.findAll();
    }

    public List<ResourceCategory> getCategoriesByType(String type) {
        return categoryRepository.findByTypeAndDeletedFalse(type);
    }

    @Transactional
    public ResourceCategory createCategory(ResourceCategory category) {
        return categoryRepository.save(category);
    }

    public List<LearningResource> getResourcesByCategory(Long categoryId) {
        return resourceRepository.findByCategoryIdAndDeletedFalse(categoryId);
    }

    public List<LearningResource> getPublishedResources() {
        return resourceRepository.findByStatusAndDeletedFalse("PUBLISHED");
    }

    public LearningResource getResourceById(Long id) {
        return resourceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Resource not found"));
    }

    @Transactional
    public LearningResource createResource(LearningResource resource) {
        return resourceRepository.save(resource);
    }

    @Transactional
    public LearningResource uploadResource(LearningResource resource, MultipartFile file) throws IOException {
        String fileUrl = fileService.uploadFile(file, "resources");
        resource.setFileUrl(fileUrl);
        resource.setFileSize(file.getSize());
        return resourceRepository.save(resource);
    }

    @Transactional
    public LearningResource updateResource(Long id, LearningResource updatedResource) {
        LearningResource resource = getResourceById(id);
        
        if (updatedResource.getTitle() != null) {
            resource.setTitle(updatedResource.getTitle());
        }
        if (updatedResource.getDescription() != null) {
            resource.setDescription(updatedResource.getDescription());
        }
        if (updatedResource.getCategoryId() != null) {
            resource.setCategoryId(updatedResource.getCategoryId());
        }
        if (updatedResource.getStatus() != null) {
            resource.setStatus(updatedResource.getStatus());
        }

        return resourceRepository.save(resource);
    }

    @Transactional
    public void deleteResource(Long id) {
        LearningResource resource = getResourceById(id);
        resource.setDeleted(true);
        resourceRepository.save(resource);
    }

    @Transactional
    public void incrementViewCount(Long id) {
        LearningResource resource = getResourceById(id);
        resource.setViewCount(resource.getViewCount() + 1);
        resourceRepository.save(resource);
    }
}
