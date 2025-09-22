package com.rentoki.wildcatsmplacebackend.exceptions;

import lombok.Getter;

@Getter
public enum ErrorMessages {
    INVALID_CREDENTIALS("Invalid credentials"),
    STUDENT_NOT_FOUND("Student not found"),
    INACTIVE_ACCOUNT("Account is deactivated");

    private final String message;

    ErrorMessages(String message) {
        this.message = message;
    }

}
