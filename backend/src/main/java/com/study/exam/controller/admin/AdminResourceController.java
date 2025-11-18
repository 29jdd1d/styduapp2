package com.study.exam.controller.admin;

import com.study.exam.common.Result;
import com.study.exam.entity.LearningResource;
import com.study.exam.service.LearningResourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/admin/resource")
@RequiredArgsConstructor
public class AdminResourceController {

    private final LearningResourceService resourceService;

    @GetMapping("/list")
    public Result<List<LearningResource>> getAllResources() {
        return Result.success(resourceService.getPublishedResources());
    }

    @GetMapping("/{id}")
    public Result<LearningResource> getResourceById(@PathVariable Long id) {
        try {
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
