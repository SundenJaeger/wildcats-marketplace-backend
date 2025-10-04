package com.rentoki.wildcatsmplacebackend.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RegisterRequest {
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;

    public RegisterRequest(Student student) {
        if (student != null && student.getUser() != null) {
            this.username = student.getUser().getUsername();
            this.password = student.getUser().getPassword();
            this.email = student.getUser().getEmail();
            this.firstName = student.getUser().getFirstName();
            this.lastName = student.getUser().getLastName();
        }
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }
}