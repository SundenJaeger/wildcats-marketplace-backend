package com.rentoki.wildcatsmplacebackend.service;

import com.rentoki.wildcatsmplacebackend.exceptions.ErrorMessages;
import com.rentoki.wildcatsmplacebackend.exceptions.ResourceImageNotFoundException;
import com.rentoki.wildcatsmplacebackend.model.ResourceImage;
import com.rentoki.wildcatsmplacebackend.repository.ResourceImageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public ResourceImage createImage(ResourceImage image) {
        return resourceImageRepository.save(image);
    }

    public ResourceImage updateImage(Integer id, ResourceImage imageDetails) {
        ResourceImage image = resourceImageRepository.findById(id)
                .orElseThrow(() -> new ResourceImageNotFoundException(ErrorMessages.RESOURCE_IMAGE_NOT_FOUND.getMessage()));

        image.setImagePath(imageDetails.getImagePath());
        image.setDisplayOrder(imageDetails.getDisplayOrder());
        image.setIsPrimary(imageDetails.getIsPrimary());

        return resourceImageRepository.save(image);
    }

    @Transactional
    public ResourceImage setAsPrimaryImage(Integer id) {
        ResourceImage image = resourceImageRepository.findById(id)
                .orElseThrow(() -> new ResourceImageNotFoundException(ErrorMessages.RESOURCE_IMAGE_NOT_FOUND.getMessage()));

        // Reset all primary images for this resource
        resourceImageRepository.clearPrimaryImages(image.getResource().getResourceId());

        // Set this image as primary
        image.setIsPrimary(true);
        return resourceImageRepository.save(image);
    }

    public void deleteImage(Integer id) {
        ResourceImage image = resourceImageRepository.findById(id)
                .orElseThrow(() -> new ResourceImageNotFoundException(ErrorMessages.RESOURCE_IMAGE_NOT_FOUND.getMessage()));
        resourceImageRepository.delete(image);
    }
}