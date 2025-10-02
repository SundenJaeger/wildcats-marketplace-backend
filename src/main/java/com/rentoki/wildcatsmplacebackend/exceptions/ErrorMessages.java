package com.rentoki.wildcatsmplacebackend.exceptions;

import lombok.Getter;

@Getter
public enum ErrorMessages {
    INVALID_CREDENTIALS("Invalid credentials"),
    STUDENT_NOT_FOUND("Student not found"),
    INACTIVE_ACCOUNT("Account is deactivated"),
    USERNAME_ALREADY_EXISTS("Username already exists"),
    EMAIL_ALREADY_EXISTS("Email already exists"),
    USER_NOT_FOUND("User not found");

    private final String message;

    ErrorMessages(String message) {
        this.message = message;
    }

}
