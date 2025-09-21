package com.rentoki.wildcatsmplacebackend.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RegisterResponse {
    private String username;
    private String email;
    private String fullname;

    public RegisterResponse(RegisterRequest request) {
        this.username = request.getUsername();
        this.email = request.getEmail();
        this.fullname = request.getFullname();
    }
}
