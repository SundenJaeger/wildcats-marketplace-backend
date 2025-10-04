package com.rentoki.wildcatsmplacebackend.controller;

import com.rentoki.wildcatsmplacebackend.model.ResourceImage;
import com.rentoki.wildcatsmplacebackend.service.ResourceImageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/resource-images")
public class ResourceImageController {
    private final ResourceImageService resourceImageService;

    public ResourceImageController(ResourceImageService resourceImageService) {
        this.resourceImageService = resourceImageService;
    }

    @GetMapping("/resource/{resourceId}")
    public ResponseEntity<List<ResourceImage>> getImagesByResource(@PathVariable Integer resourceId) {
        List<ResourceImage> images = resourceImageService.getImagesByResource(resourceId);
        return ResponseEntity.ok(images);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResourceImage> getImageById(@PathVariable Integer id) {
        ResourceImage image = resourceImageService.getImageById(id);
        return ResponseEntity.ok(image);
    }

    @PostMapping
    public ResponseEntity<ResourceImage> createImage(@RequestBody ResourceImage image) {
        ResourceImage createdImage = resourceImageService.createImage(image);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdImage);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResourceImage> updateImage(@PathVariable Integer id, @RequestBody ResourceImage imageDetails) {
        ResourceImage updatedImage = resourceImageService.updateImage(id, imageDetails);
        return ResponseEntity.ok(updatedImage);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteImage(@PathVariable Integer id) {
        resourceImageService.deleteImage(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/set-primary")
    public ResponseEntity<ResourceImage> setAsPrimaryImage(@PathVariable Integer id) {
        ResourceImage image = resourceImageService.setAsPrimaryImage(id);
        return ResponseEntity.ok(image);
    }
}