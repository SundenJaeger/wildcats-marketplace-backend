package com.rentoki.wildcatsmplacebackend.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginResponse {
    private Integer userId;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String fullName;
    private String userType;
    private Integer studentId;
    private Boolean isVerified;
    private String enrollmentStatus;
    private String program;
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
