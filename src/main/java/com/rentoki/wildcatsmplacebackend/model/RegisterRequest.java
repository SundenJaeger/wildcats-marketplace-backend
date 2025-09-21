package com.rentoki.wildcatsmplacebackend.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RegisterRequest {
    private String username;
    private String password;
    private String email;
    private String fullname;

    public RegisterRequest(Student student) {
        this.username = student.getUsername();
        this.password = student.getPassword();
        this.email = student.getEmail();
        this.fullname = student.getFullname();
    }
}
