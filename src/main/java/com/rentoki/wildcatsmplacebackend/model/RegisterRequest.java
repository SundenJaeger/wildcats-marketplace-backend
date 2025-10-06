package com.rentoki.wildcatsmplacebackend.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Schema(description = "Student registration request")
public class RegisterRequest {

    @Schema(
            description = "Unique username",
            example = "jane_doe",
            requiredMode = Schema.RequiredMode.REQUIRED,
            minLength = 3,
            maxLength = 50
    )
    private String username;

    @Schema(
            description = "Password",
            example = "password123",
            requiredMode = Schema.RequiredMode.REQUIRED,
            minLength = 6
    )
    private String password;

    @Schema(
            description = "Email address",
            example = "jane@student.edu",
            requiredMode = Schema.RequiredMode.REQUIRED,
            pattern = "^[A-Za-z0-9+_.-]+@(.+)$"
    )
    private String email;

    @Schema(
            description = "First name",
            example = "Jane",
            requiredMode = Schema.RequiredMode.REQUIRED,
            minLength = 1,
            maxLength = 50
    )
    private String firstName;

    @Schema(
            description = "Last name",
            example = "Doe",
            requiredMode = Schema.RequiredMode.REQUIRED,
            minLength = 1,
            maxLength = 50
    )
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