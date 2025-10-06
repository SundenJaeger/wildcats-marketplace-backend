package com.rentoki.wildcatsmplacebackend.controller;

import com.rentoki.wildcatsmplacebackend.model.LoginRequest;
import com.rentoki.wildcatsmplacebackend.model.LoginResponse;
import com.rentoki.wildcatsmplacebackend.model.RegisterRequest;
import com.rentoki.wildcatsmplacebackend.model.RegisterResponse;
import com.rentoki.wildcatsmplacebackend.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "1. Authentication", description = "User authentication and registration APIs")
public class AuthController {
    private final StudentService studentService;

    public AuthController(StudentService studentService) {
        this.studentService = studentService;
    }

    @Operation(
            summary = "Register a new student",
            description = "Creates a new student account. Students start with verified=false and need admin approval."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Student registered successfully",
                    content = @Content(schema = @Schema(implementation = RegisterResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input data",
                    content = @Content(
                            examples = @ExampleObject(value = "{\"message\": \"Email already exists\"}")
                    )
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Username or email already exists",
                    content = @Content(
                            examples = @ExampleObject(value = "{\"message\": \"Username already exists\"}")
                    )
            )
    })
    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Student registration details",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = RegisterRequest.class),
                            examples = @ExampleObject(
                                    value = """
                            {
                                "username": "jane_doe",
                                "password": "password123",
                                "email": "jane@student.edu",
                                "firstName": "Jane",
                                "lastName": "Doe"
                            }
                            """
                            )
                    )
            )
            @RequestBody RegisterRequest request) {
        RegisterResponse response = studentService.registerStudent(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
            summary = "Login user",
            description = "Authenticates a user (student or admin) using username/email and password"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Login successful",
                    content = @Content(schema = @Schema(implementation = LoginResponse.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Invalid credentials",
                    content = @Content(
                            examples = @ExampleObject(value = "{\"message\": \"Invalid credentials\"}")
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Account not verified",
                    content = @Content(
                            examples = @ExampleObject(value = "{\"message\": \"Account is not active\"}")
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found",
                    content = @Content(
                            examples = @ExampleObject(value = "{\"message\": \"Student not found\"}")
                    )
            )
    })
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Login credentials",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = LoginRequest.class),
                            examples = @ExampleObject(
                                    value = """
                            {
                                "username": "jane_doe",
                                "password": "password123"
                            }
                            """
                            )
                    )
            )
            @RequestBody LoginRequest request) {
        LoginResponse response = studentService.loginStudent(request);
        return ResponseEntity.ok(response);
    }
}