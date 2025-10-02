package com.rentoki.wildcatsmplacebackend.repository;

import com.rentoki.wildcatsmplacebackend.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Integer> {
    List<Report> findByStatus(Report.ReportStatus status);
    List<Report> findByStudentStudentId(Integer studentId);
    List<Report> findByResourceResourceId(Integer resourceId);
}