package com.rentoki.wildcatsmplacebackend.model;

import jakarta.validation.constraints.NotNull;

public record ResourceImageRequest(
        @NotNull(message = "Resource ID is required")
        ResourceRef resource,

        @NotNull(message = "Image path is required")
        String imagePath,

        Integer displayOrder
) {
    public record ResourceRef(Integer resourceId) {}

    public Integer resourceId() {
        return resource != null ? resource.resourceId() : null;
    }
}