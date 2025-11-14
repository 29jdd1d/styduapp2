package com.study.exam.controller;

import com.study.exam.common.Result;
import com.study.exam.entity.LearningResource;
import com.study.exam.entity.ResourceCategory;
import com.study.exam.service.LearningResourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/resource")
@RequiredArgsConstructor
public class LearningResourceController {

    private final LearningResourceService resourceService;

    @GetMapping("/category")
    public Result<List<ResourceCategory>> getAllCategories() {
        return Result.success(resourceService.getAllCategories());
    }

    @GetMapping("/category/type/{type}")
    public Result<List<ResourceCategory>> getCategoriesByType(@PathVariable String type) {
        return Result.success(resourceService.getCategoriesByType(type));
    }

    @PostMapping("/category")
    public Result<ResourceCategory> createCategory(@RequestBody ResourceCategory category) {
        return Result.success(resourceService.createCategory(category));
    }

    @GetMapping("/list")
    public Result<List<LearningResource>> getPublishedResources() {
        return Result.success(resourceService.getPublishedResources());
    }

    @GetMapping("/category/{categoryId}")
    public Result<List<LearningResource>> getResourcesByCategory(@PathVariable Long categoryId) {
        return Result.success(resourceService.getResourcesByCategory(categoryId));
    }

    @GetMapping("/{id}")
    public Result<LearningResource> getResourceById(@PathVariable Long id) {
        try {
            resourceService.incrementViewCount(id);
            return Result.success(resourceService.getResourceById(id));
        } catch (Exception e) {
            return Result.error("Failed to get resource: " + e.getMessage());
        }
    }

    @PostMapping
    public Result<LearningResource> createResource(@RequestBody LearningResource resource) {
        return Result.success(resourceService.createResource(resource));
    }

    @PostMapping("/upload")
    public Result<LearningResource> uploadResource(
            @RequestPart("resource") LearningResource resource,
            @RequestPart("file") MultipartFile file) {
        try {
            LearningResource created = resourceService.uploadResource(resource, file);
            return Result.success("Resource uploaded successfully", created);
        } catch (Exception e) {
            return Result.error("Failed to upload resource: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public Result<LearningResource> updateResource(
            @PathVariable Long id,
            @RequestBody LearningResource resource) {
        try {
            return Result.success(resourceService.updateResource(id, resource));
        } catch (Exception e) {
            return Result.error("Failed to update resource: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public Result<Void> deleteResource(@PathVariable Long id) {
        try {
            resourceService.deleteResource(id);
            return Result.success("Resource deleted successfully", null);
        } catch (Exception e) {
            return Result.error("Failed to delete resource: " + e.getMessage());
        }
    }
}
