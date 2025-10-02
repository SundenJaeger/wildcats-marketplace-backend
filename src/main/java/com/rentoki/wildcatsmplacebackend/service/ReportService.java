package com.rentoki.wildcatsmplacebackend.service;

import com.rentoki.wildcatsmplacebackend.exceptions.ErrorMessages;
import com.rentoki.wildcatsmplacebackend.exceptions.ReportNotFoundException;
import com.rentoki.wildcatsmplacebackend.model.Report;
import com.rentoki.wildcatsmplacebackend.repository.ReportRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReportService {
    private final ReportRepository reportRepository;

    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
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

    public Report createReport(Report report) {
        report.setStatus(Report.ReportStatus.PENDING);
        report.setDateReported(LocalDateTime.now());
        return reportRepository.save(report);
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

        // Note: You'll need to inject AdminService and get the admin entity
        // For now, we'll just set the admin ID
        // report.setAdmin(adminService.getAdminById(adminId));

        report.setStatus(Report.ReportStatus.UNDER_REVIEW);
        return reportRepository.save(report);
    }

    public void deleteReport(Integer id) {
        Report report = reportRepository.findById(id)
                .orElseThrow(() -> new ReportNotFoundException(ErrorMessages.REPORT_NOT_FOUND.getMessage()));
        reportRepository.delete(report);
    }
}