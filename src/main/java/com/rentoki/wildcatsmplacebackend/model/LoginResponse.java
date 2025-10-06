package com.rentoki.wildcatsmplacebackend.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Schema(description = "Login successful response")
public class LoginResponse {

    @Schema(description = "User ID", example = "1")
    private Integer userId;

    @Schema(description = "Username", example = "john_doe")
    private String username;

    @Schema(description = "Email address", example = "john@student.edu")
    private String email;

    @Schema(description = "First name", example = "John")
    private String firstName;

    @Schema(description = "Last name", example = "Doe")
    private String lastName;

    @Schema(description = "Full name", example = "John Doe")
    private String fullName;

    @Schema(description = "User type (S=Student, A=Admin)", example = "S", allowableValues = {"S", "A"})
    private String userType;

    @Schema(description = "Student ID (if user is a student)", example = "1")
    private Integer studentId;

    @Schema(description = "Student verification status", example = "true")
    private Boolean isVerified;

    @Schema(description = "Student enrollment status", example = "Active")
    private String enrollmentStatus;

    @Schema(description = "Student program", example = "Computer Science")
    private String program;

    @Schema(description = "Student year level", example = "Sophomore")
    private String yearLevel;

    public LoginResponse(User user, Student student) {
        if (user != null) {
            this.userId = user.getUserId();
            this.username = user.getUsername();
            this.email = user.getEmail();
            this.firstName = user.getFirstName();
            this.lastName = user.getLastName();
            this.fullName = user.getFirstName() + " " + user.getLastName();
            this.userType = user.getType();

            if (student != null && "S".equals(user.getType())) {
                this.studentId = student.getStudentId();
                this.isVerified = student.getIsVerified();
                this.enrollmentStatus = student.getEnrollmentStatus();
                this.program = student.getProgram();
                this.yearLevel = student.getYearLevel();
            }
        }
    }

    public LoginResponse(User user) {
        if (user != null) {
            this.userId = user.getUserId();
            this.username = user.getUsername();
            this.email = user.getEmail();
            this.firstName = user.getFirstName();
            this.lastName = user.getLastName();
            this.fullName = user.getFirstName() + " " + user.getLastName();
            this.userType = user.getType();
        }
    }
}
