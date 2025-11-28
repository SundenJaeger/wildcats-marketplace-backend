package com.rentoki.wildcatsmplacebackend.service;

import com.rentoki.wildcatsmplacebackend.exceptions.ErrorMessages;
import com.rentoki.wildcatsmplacebackend.exceptions.CategoryNotFoundException;
import com.rentoki.wildcatsmplacebackend.model.Category;
import com.rentoki.wildcatsmplacebackend.model.CategoryRequest;
import com.rentoki.wildcatsmplacebackend.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public List<Category> getMainCategories() {
        return categoryRepository.findByParentCategoryIsNull();
    }

    public List<Category> getSubcategories(Integer parentId) {
        return categoryRepository.findByParentCategoryCategoryId(parentId);
    }

    public List<Category> getActiveCategories() {
        return categoryRepository.findByIsActiveTrue();
    }

    public Category getCategoryById(Integer id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(ErrorMessages.CATEGORY_NOT_FOUND.getMessage()));
    }

    public Category createCategory(CategoryRequest request) {
        Category category = new Category();
        category.setCategoryName(request.categoryName());
        category.setDescription(request.description());
        category.setIsActive(request.isActive() != null ? request.isActive() : true);

        if (request.parentCategoryId() != null) {
            Category parent = categoryRepository.findById(request.parentCategoryId())
                    .orElseThrow(() -> new CategoryNotFoundException(ErrorMessages.CATEGORY_NOT_FOUND.getMessage()));
            category.setParentCategory(parent);
        }

        return categoryRepository.save(category);
    }

    public Category updateCategory(Integer id, CategoryRequest request) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(ErrorMessages.CATEGORY_NOT_FOUND.getMessage()));

        category.setCategoryName(request.categoryName());
        category.setDescription(request.description());
        category.setIsActive(request.isActive() != null ? request.isActive() : category.getIsActive());

        if (request.parentCategory() != null) {
            if (request.parentCategoryId() != null) {
                Category parent = categoryRepository.findById(request.parentCategoryId())
                        .orElseThrow(() -> new CategoryNotFoundException(ErrorMessages.CATEGORY_NOT_FOUND.getMessage()));
                category.setParentCategory(parent);
            } else {
                category.setParentCategory(null);
            }
        }

        return categoryRepository.save(category);
    }

    public void deleteCategory(Integer id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(ErrorMessages.CATEGORY_NOT_FOUND.getMessage()));
        categoryRepository.delete(category);
    }

    public Category toggleCategoryStatus(Integer id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(ErrorMessages.CATEGORY_NOT_FOUND.getMessage()));

        category.setIsActive(!category.getIsActive());
        return categoryRepository.save(category);
    }
}