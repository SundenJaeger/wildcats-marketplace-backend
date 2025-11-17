package com.rentoki.wildcatsmplacebackend.model;

import java.time.LocalDateTime;

public record ReportResponse(Integer reportId, String description, Report.ReportStatus reportStatus, LocalDateTime dateReported, LocalDateTime dateResolved) {
}
