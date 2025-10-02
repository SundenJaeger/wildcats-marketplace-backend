package com.rentoki.wildcatsmplacebackend.service;

import com.rentoki.wildcatsmplacebackend.exceptions.ErrorMessages;
import com.rentoki.wildcatsmplacebackend.exceptions.CategoryNotFoundException;
import com.rentoki.wildcatsmplacebackend.model.Category;
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

    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    public Category updateCategory(Integer id, Category categoryDetails) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(ErrorMessages.CATEGORY_NOT_FOUND.getMessage()));

        category.setCategoryName(categoryDetails.getCategoryName());
        category.setParentCategory(categoryDetails.getParentCategory());
        category.setDescription(categoryDetails.getDescription());
        category.setIsActive(categoryDetails.getIsActive());

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