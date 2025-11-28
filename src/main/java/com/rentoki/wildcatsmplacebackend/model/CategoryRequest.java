package com.rentoki.wildcatsmplacebackend.model;

import jakarta.validation.constraints.NotBlank;

public record CategoryRequest(
        @NotBlank(message = "Category name is required")
        String categoryName,

        String description,

        Boolean isActive,

        CategoryRef parentCategory
) {
    public record CategoryRef(Integer categoryId) {}

    public Integer parentCategoryId() {
        return parentCategory != null ? parentCategory.categoryId() : null;
    }
}