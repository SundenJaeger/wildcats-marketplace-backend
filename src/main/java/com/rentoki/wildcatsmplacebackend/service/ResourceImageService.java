package com.rentoki.wildcatsmplacebackend.service;

import com.rentoki.wildcatsmplacebackend.exceptions.ErrorMessages;
import com.rentoki.wildcatsmplacebackend.exceptions.ResourceImageNotFoundException;
import com.rentoki.wildcatsmplacebackend.model.Resource;
import com.rentoki.wildcatsmplacebackend.model.ResourceImage;
import com.rentoki.wildcatsmplacebackend.model.ResourceImageRequest;
import com.rentoki.wildcatsmplacebackend.repository.ResourceImageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResourceImageService {
    private final ResourceImageRepository resourceImageRepository;

    public ResourceImageService(ResourceImageRepository resourceImageRepository) {
        this.resourceImageRepository = resourceImageRepository;
    }

    public List<ResourceImage> getImagesByResource(Integer resourceId) {
        return resourceImageRepository.findByResourceResourceId(resourceId);
    }

    public ResourceImage getImageById(Integer id) {
        return resourceImageRepository.findById(id)
                .orElseThrow(() -> new ResourceImageNotFoundException(ErrorMessages.RESOURCE_IMAGE_NOT_FOUND.getMessage()));
    }

    public ResourceImage createImage(ResourceImageRequest request) {
        ResourceImage image = new ResourceImage();
        image.setImagePath(request.imagePath());
        image.setDisplayOrder(request.displayOrder() != null ? request.displayOrder() : 0);

        if (request.resourceId() != null) {
            Resource resource = new Resource();
            resource.setResourceId(request.resourceId());
            image.setResource(resource);
        }

        return resourceImageRepository.save(image);
    }

    public ResourceImage updateImage(Integer id, ResourceImageRequest request) {
        ResourceImage image = resourceImageRepository.findById(id)
                .orElseThrow(() -> new ResourceImageNotFoundException(ErrorMessages.RESOURCE_IMAGE_NOT_FOUND.getMessage()));

        image.setImagePath(request.imagePath());
        image.setDisplayOrder(request.displayOrder() != null ? request.displayOrder() : image.getDisplayOrder());

        if (request.resourceId() != null) {
            Resource resource = new Resource();
            resource.setResourceId(request.resourceId());
            image.setResource(resource);
        }

        return resourceImageRepository.save(image);
    }

    public void deleteImage(Integer id) {
        ResourceImage image = resourceImageRepository.findById(id)
                .orElseThrow(() -> new ResourceImageNotFoundException(ErrorMessages.RESOURCE_IMAGE_NOT_FOUND.getMessage()));
        resourceImageRepository.delete(image);
    }
}