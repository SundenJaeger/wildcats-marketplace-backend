package com.rentoki.wildcatsmplacebackend.model;

import java.time.LocalDateTime;

public record ReportResponse(
        Integer reportId,
        String reason,
        String description,
        Report.ReportStatus status,
        LocalDateTime dateReported,
        LocalDateTime dateResolved,
        Integer studentId,
        Integer resourceId,
        Integer adminId,
        StudentInfo student,
        ResourceInfo resource
) {
    public record StudentInfo(
            Integer studentId,
            String firstName,
            String lastName,
            String email
    ) {}

    public record ResourceInfo(
            Integer resourceId,
            String title
    ) {}
}