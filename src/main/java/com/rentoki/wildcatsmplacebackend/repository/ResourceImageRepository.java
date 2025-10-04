package com.rentoki.wildcatsmplacebackend.repository;

import com.rentoki.wildcatsmplacebackend.model.ResourceImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ResourceImageRepository extends JpaRepository<ResourceImage, Integer> {
    List<ResourceImage> findByResourceResourceId(Integer resourceId);
    List<ResourceImage> findByIsPrimaryTrue();

    @Modifying
    @Query("UPDATE ResourceImage ri SET ri.isPrimary = false WHERE ri.resource.resourceId = :resourceId")
    void clearPrimaryImages(@Param("resourceId") Integer resourceId);
}