package com.rentoki.wildcatsmplacebackend.repository;

import com.rentoki.wildcatsmplacebackend.model.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ResourceRepository extends JpaRepository<Resource, Integer> {
    List<Resource> findByStudentStudentId(Integer studentId);
    List<Resource> findByCategoryCategoryId(Integer categoryId);
    List<Resource> findByStatus(Resource.ResourceStatus status);
    List<Resource> findByTitleContainingIgnoreCase(String title);
}