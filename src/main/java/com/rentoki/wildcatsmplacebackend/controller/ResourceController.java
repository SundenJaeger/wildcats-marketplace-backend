package com.rentoki.wildcatsmplacebackend.controller;

import com.rentoki.wildcatsmplacebackend.model.Resource;
import com.rentoki.wildcatsmplacebackend.service.ResourceService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/resources")
@Tag(name = "3. Marketplace", description = "Resource marketplace APIs")
public class ResourceController {
    private final ResourceService resourceService;

    public ResourceController(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    @GetMapping
    public ResponseEntity<List<Resource>> getAllResources() {
        List<Resource> resources = resourceService.getAllResources();
        return ResponseEntity.ok(resources);
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Resource>> getResourcesByStudent(@PathVariable Integer studentId) {
        List<Resource> resources = resourceService.getResourcesByStudent(studentId);
        return ResponseEntity.ok(resources);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<Resource>> getResourcesByCategory(@PathVariable Integer categoryId) {
        List<Resource> resources = resourceService.getResourcesByCategory(categoryId);
        return ResponseEntity.ok(resources);
    }

    @GetMapping("/available")
    public ResponseEntity<List<Resource>> getAvailableResources() {
        List<Resource> resources = resourceService.getAvailableResources();
        return ResponseEntity.ok(resources);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Resource>> searchResources(@RequestParam String keyword) {
        List<Resource> resources = resourceService.searchResources(keyword);
        return ResponseEntity.ok(resources);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resource> getResourceById(@PathVariable Integer id) {
        Resource resource = resourceService.getResourceById(id);
        return ResponseEntity.ok(resource);
    }

    @PostMapping
    public ResponseEntity<Resource> createResource(@RequestBody Resource resource) {
        Resource createdResource = resourceService.createResource(resource);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdResource);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Resource> updateResource(@PathVariable Integer id, @RequestBody Resource resourceDetails) {
        Resource updatedResource = resourceService.updateResource(id, resourceDetails);
        return ResponseEntity.ok(updatedResource);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Resource> updateResourceStatus(@PathVariable Integer id, @RequestBody StatusUpdateRequest request) {
        Resource resource = resourceService.updateResourceStatus(id, request.status());
        return ResponseEntity.ok(resource);
    }

    @PatchMapping("/{id}/condition")
    public ResponseEntity<Resource> updateResourceCondition(@PathVariable Integer id, @RequestBody ConditionUpdateRequest request) {
        Resource resource = resourceService.updateResourceCondition(id, request.condition());
        return ResponseEntity.ok(resource);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteResource(@PathVariable Integer id) {
        resourceService.deleteResource(id);
        return ResponseEntity.noContent().build();
    }
    
    public record StatusUpdateRequest(Resource.ResourceStatus status) {}
    public record ConditionUpdateRequest(Resource.ItemCondition condition) {}
}