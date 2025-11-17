package com.rentoki.wildcatsmplacebackend.controller;

import com.rentoki.wildcatsmplacebackend.model.CreateReportRequest;
import com.rentoki.wildcatsmplacebackend.model.Report;
import com.rentoki.wildcatsmplacebackend.model.ReportResponse;
import com.rentoki.wildcatsmplacebackend.service.ReportService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
@Tag(name = "6. Reports", description = "Content reporting and moderation APIs")
public class ReportController {
    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping
    public ResponseEntity<List<Report>> getAllReports() {
        List<Report> reports = reportService.getAllReports();
        return ResponseEntity.ok(reports);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Report>> getReportsByStatus(@PathVariable Report.ReportStatus status) {
        List<Report> reports = reportService.getReportsByStatus(status);
        return ResponseEntity.ok(reports);
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Report>> getReportsByStudent(@PathVariable Integer studentId) {
        List<Report> reports = reportService.getReportsByStudent(studentId);
        return ResponseEntity.ok(reports);
    }

    @GetMapping("/resource/{resourceId}")
    public ResponseEntity<List<Report>> getReportsByResource(@PathVariable Integer resourceId) {
        List<Report> reports = reportService.getReportsByResource(resourceId);
        return ResponseEntity.ok(reports);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Report> getReportById(@PathVariable Integer id) {
        Report report = reportService.getReportById(id);
        return ResponseEntity.ok(report);
    }

    @PostMapping
    public ResponseEntity<ReportResponse> createReport(@RequestBody CreateReportRequest request) {
        ReportResponse createdReport = reportService.createReport(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdReport);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Report> updateReportStatus(@PathVariable Integer id, @RequestBody StatusUpdateRequest request) {
        Report report = reportService.updateReportStatus(id, request.status());
        return ResponseEntity.ok(report);
    }

    @PatchMapping("/{id}/assign/{adminId}")
    public ResponseEntity<Report> assignReportToAdmin(@PathVariable Integer id, @PathVariable Integer adminId) {
        Report report = reportService.assignReportToAdmin(id, adminId);
        return ResponseEntity.ok(report);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReport(@PathVariable Integer id) {
        reportService.deleteReport(id);
        return ResponseEntity.noContent().build();
    }

    // Request records
    public record StatusUpdateRequest(Report.ReportStatus status) {}
}