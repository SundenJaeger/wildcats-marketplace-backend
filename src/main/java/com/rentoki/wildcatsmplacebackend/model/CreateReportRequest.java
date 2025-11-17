package com.rentoki.wildcatsmplacebackend.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateReportRequest(
        @NotBlank(message = "Reason is required")
        String reason,

        String description,

        @NotNull(message = "Student is required")
        StudentRef student,

        @NotNull(message = "Resource is required")
        ResourceRef resource
) {
    public record StudentRef(Integer studentId) {}
    public record ResourceRef(Integer resourceId) {}

    public Integer studentId() {
        return student != null ? student.studentId() : null;
    }

    public Integer resourceId() {
        return resource != null ? resource.resourceId() : null;
    }
}