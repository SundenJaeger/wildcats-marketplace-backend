package com.rentoki.wildcatsmplacebackend.service;

import com.rentoki.wildcatsmplacebackend.exceptions.*;
import com.rentoki.wildcatsmplacebackend.model.*;
import com.rentoki.wildcatsmplacebackend.repository.AdminRepository;
import com.rentoki.wildcatsmplacebackend.repository.ReportRepository;
import com.rentoki.wildcatsmplacebackend.repository.ResourceRepository;
import com.rentoki.wildcatsmplacebackend.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReportService {
    private final ReportRepository reportRepository;
    private final AdminRepository adminRepository;
    private final StudentRepository studentRepository;
    private final ResourceRepository resourceRepository;

    public ReportService(ReportRepository reportRepository, AdminRepository adminRepository, StudentRepository studentRepository, ResourceRepository resourceRepository) {
        this.reportRepository = reportRepository;
        this.adminRepository = adminRepository;
        this.studentRepository = studentRepository;
        this.resourceRepository = resourceRepository;
    }

    public List<Report> getAllReports() {
        return reportRepository.findAll();
    }

    public List<Report> getReportsByStatus(Report.ReportStatus status) {
        return reportRepository.findByStatus(status);
    }

    public List<Report> getReportsByStudent(Integer studentId) {
        return reportRepository.findByStudentStudentId(studentId);
    }

    public List<Report> getReportsByResource(Integer resourceId) {
        return reportRepository.findByResourceResourceId(resourceId);
    }

    public Report getReportById(Integer id) {
        return reportRepository.findById(id)
                .orElseThrow(() -> new ReportNotFoundException(ErrorMessages.REPORT_NOT_FOUND.getMessage()));
    }

    public ReportResponse createReport(CreateReportRequest createReportRequest) {
        Student student = studentRepository.findById(createReportRequest.studentId())
                .orElseThrow(() -> new StudentNotFoundException("Student not found with ID: " + createReportRequest.studentId()));

        Resource resource = resourceRepository.findById(createReportRequest.resourceId())
                .orElseThrow(() -> new ResourceNotFoundException("Resource not found with ID: " + createReportRequest.resourceId()));

        Report report = new Report();
        report.setReason(createReportRequest.reason());
        report.setDescription(createReportRequest.description());
        report.setStudent(student);
        report.setResource(resource);
        report.setStatus(Report.ReportStatus.PENDING);
        report.setDateReported(LocalDateTime.now());

        Report savedReport = reportRepository.save(report);

        return new ReportResponse(savedReport.getReportId(), savedReport.getDescription(), savedReport.getStatus(), savedReport.getDateReported(), savedReport.getDateResolved()
        );
    }

    public Report updateReportStatus(Integer id, Report.ReportStatus status) {
        Report report = reportRepository.findById(id)
                .orElseThrow(() -> new ReportNotFoundException(ErrorMessages.REPORT_NOT_FOUND.getMessage()));

        report.setStatus(status);
        if (status == Report.ReportStatus.RESOLVED || status == Report.ReportStatus.DISMISSED) {
            report.setDateResolved(LocalDateTime.now());
        }

        return reportRepository.save(report);
    }

    public Report assignReportToAdmin(Integer reportId, Integer adminId) {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new ReportNotFoundException(ErrorMessages.REPORT_NOT_FOUND.getMessage()));

        Admin admin = adminRepository.findById(adminId).orElseThrow(() -> new AdminNotFoundException("Admin not fonud with ID: " + adminId));

        report.setAdmin(admin);
        report.setStatus(Report.ReportStatus.UNDER_REVIEW);
        return reportRepository.save(report);
    }

    public void deleteReport(Integer id) {
        Report report = reportRepository.findById(id)
                .orElseThrow(() -> new ReportNotFoundException(ErrorMessages.REPORT_NOT_FOUND.getMessage()));
        reportRepository.delete(report);
    }
}