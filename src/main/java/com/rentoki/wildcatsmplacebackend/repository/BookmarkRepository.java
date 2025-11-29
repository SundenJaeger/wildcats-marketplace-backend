package com.rentoki.wildcatsmplacebackend.repository;

import com.rentoki.wildcatsmplacebackend.model.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Integer> {

    List<Bookmark> findByStudentStudentId(Integer studentId);

    Optional<Bookmark> findByStudentStudentIdAndResourceResourceId(Integer studentId, Integer resourceId);

    boolean existsByStudentStudentIdAndResourceResourceId(Integer studentId, Integer resourceId);

    void deleteByStudentStudentIdAndResourceResourceId(Integer studentId, Integer resourceId);
}