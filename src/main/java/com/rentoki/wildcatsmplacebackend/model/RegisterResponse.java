package com.rentoki.wildcatsmplacebackend.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RegisterResponse {
    private Integer studentId;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String fullName;
    private String enrollmentStatus;
    private String program;
    private String yearLevel;
    private Boolean isVerified;

    public RegisterResponse(Student student) {
        if (student != null && student.getUser() != null) {
            this.studentId = student.getStudentId();
            this.username = student.getUser().getUsername();
            this.email = student.getUser().getEmail();
            this.firstName = student.getUser().getFirstName();
            this.lastName = student.getUser().getLastName();
            this.fullName = student.getUser().getFirstName() + " " + student.getUser().getLastName();
            this.enrollmentStatus = student.getEnrollmentStatus();
            this.program = student.getProgram();
            this.yearLevel = student.getYearLevel();
            this.isVerified = student.getIsVerified();
        }
    }

    public RegisterResponse(User user, Student student) {
        if (user != null) {
            this.studentId = student != null ? student.getStudentId() : null;
            this.username = user.getUsername();
            this.email = user.getEmail();
            this.firstName = user.getFirstName();
            this.lastName = user.getLastName();
            this.fullName = user.getFirstName() + " " + user.getLastName();

            if (student != null) {
                this.enrollmentStatus = student.getEnrollmentStatus();
                this.program = student.getProgram();
                this.yearLevel = student.getYearLevel();
                this.isVerified = student.getIsVerified();
            } else {
                this.enrollmentStatus = "Active";
                this.program = "Undecided";
                this.yearLevel = "Freshman";
                this.isVerified = false;
            }
        }
    }
}