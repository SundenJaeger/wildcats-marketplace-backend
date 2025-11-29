package com.rentoki.wildcatsmplacebackend.exceptions;

import lombok.Getter;

@Getter
public enum ErrorMessages {
    INVALID_CREDENTIALS("Invalid credentials"),
    STUDENT_NOT_FOUND("Student not found"),
    INACTIVE_ACCOUNT("Account is deactivated"),
    USERNAME_ALREADY_EXISTS("Username already exists"),
    EMAIL_ALREADY_EXISTS("Email already exists"),
    USER_NOT_FOUND("User not found"),
    CATEGORY_NOT_FOUND("Category not found"),
    RESOURCE_NOT_FOUND("Resource not found"),
    VERIFICATION_REQUEST_NOT_FOUND("Verification request not found"),
    REPORT_NOT_FOUND("Report not found"),
    ADMIN_NOT_FOUND("Admin not found"),
    STUDENT_NOT_VERIFIED("Student is not verified"),
    RESOURCE_IMAGE_NOT_FOUND("Resource image not found"),
    RESOURCE_ALREADY_BOOKMARKED("Resource already bookmarked");

    private final String message;

    ErrorMessages(String message) {
        this.message = message;
    }

}
