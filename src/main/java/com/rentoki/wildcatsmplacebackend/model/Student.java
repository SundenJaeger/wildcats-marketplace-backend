package com.rentoki.wildcatsmplacebackend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "fullname")
    private String fullname;

    @Column(name = "is_active")
    private boolean isActive = true;

    public Student(RegisterRequest request) {
        this.username = request.getUsername();
        this.password = request.getPassword();
        this.email = request.getEmail();
        this.fullname = request.getFullname();
    }
}
