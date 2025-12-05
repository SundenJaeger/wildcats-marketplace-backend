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

    // Mapper method to convert Report entity to ReportResponse DTO
    public ReportResponse mapToResponse(Report report) {
        ReportResponse.StudentInfo studentInfo = null;
        if (report.getStudent() != null && report.getStudent().getUser() != null) {
            User user = report.getStudent().getUser();
            studentInfo = new ReportResponse.StudentInfo(
                    report.getStudent().getStudentId(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getEmail()
            );
        }

        ReportResponse.ResourceInfo resourceInfo = null;
        if (report.getResource() != null) {
            resourceInfo = new ReportResponse.ResourceInfo(
                    report.getResource().getResourceId(),
                    report.getResource().getTitle()
            );
        }

        return new ReportResponse(
                report.getReportId(),
                report.getReason(),
                report.getDescription(),
                report.getStatus(),
                report.getDateReported(),
                report.getDateResolved(),
                report.getStudent() != null ? report.getStudent().getStudentId() : null,
                report.getResource() != null ? report.getResource().getResourceId() : null,
                report.getAdmin() != null ? report.getAdmin().getAdminId() : null,
                studentInfo,
                resourceInfo
        );
    }

    public List<Report> getAllReports() {
        return reportRepository.findAll();
    }

    // New method that returns ReportResponse with full details
    public List<ReportResponse> getAllReportsWithDetails() {
        return reportRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public List<Report> getReportsByStatus(Report.ReportStatus status) {
        return reportRepository.findByStatus(status);
    }

    // New method that returns ReportResponse with full details
    public List<ReportResponse> getReportsByStatusWithDetails(Report.ReportStatus status) {
        return reportRepository.findByStatus(status)
                .stream()
                .map(this::mapToResponse)
                .toList();
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

    // New method that returns ReportResponse with full details
    public ReportResponse getReportByIdWithDetails(Integer id) {
        Report report = reportRepository.findById(id)
                .orElseThrow(() -> new ReportNotFoundException(ErrorMessages.REPORT_NOT_FOUND.getMessage()));
        return mapToResponse(report);
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

        // Return the full ReportResponse with all details
        return mapToResponse(savedReport);
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

        Admin admin = adminRepository.findById(adminId).orElseThrow(() -> new AdminNotFoundException("Admin not found with ID: " + adminId));

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