package com.rentoki.wildcatsmplacebackend.repository;

import com.rentoki.wildcatsmplacebackend.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    List<Category> findByParentCategoryIsNull();
    List<Category> findByParentCategoryCategoryId(Integer parentId);
    List<Category> findByIsActiveTrue();
}