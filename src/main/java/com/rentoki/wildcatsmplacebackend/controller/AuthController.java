package com.rentoki.wildcatsmplacebackend.controller;

import com.rentoki.wildcatsmplacebackend.model.LoginRequest;
import com.rentoki.wildcatsmplacebackend.model.LoginResponse;
import com.rentoki.wildcatsmplacebackend.model.RegisterRequest;
import com.rentoki.wildcatsmplacebackend.model.RegisterResponse;
import com.rentoki.wildcatsmplacebackend.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final StudentService studentService;

    public AuthController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        LoginResponse response = studentService.loginStudent(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest request) {
        RegisterResponse response = studentService.registerStudent(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}