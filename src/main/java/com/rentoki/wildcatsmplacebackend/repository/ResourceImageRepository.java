package com.rentoki.wildcatsmplacebackend.repository;

import com.rentoki.wildcatsmplacebackend.model.ResourceImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResourceImageRepository extends JpaRepository<ResourceImage, Integer> {
    List<ResourceImage> findByResourceResourceId(Integer resourceId);
}