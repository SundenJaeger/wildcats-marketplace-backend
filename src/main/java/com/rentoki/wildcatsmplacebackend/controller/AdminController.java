package com.rentoki.wildcatsmplacebackend.controller;

import com.rentoki.wildcatsmplacebackend.model.Admin;
import com.rentoki.wildcatsmplacebackend.model.RegisterRequest;
import com.rentoki.wildcatsmplacebackend.model.RegisterResponse;
import com.rentoki.wildcatsmplacebackend.model.User;
import com.rentoki.wildcatsmplacebackend.service.AdminService;
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

import java.util.List;

@RestController
@RequestMapping("/api/admins")
@Tag(name = "7. Administration", description = "Admin management APIs")
public class AdminController {
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping
    public ResponseEntity<List<Admin>> getAllAdmins() {
        List<Admin> admins = adminService.getAllAdmins();
        return ResponseEntity.ok(admins);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Admin> getAdminById(@PathVariable Integer id) {
        Admin admin = adminService.getAdminById(id)
                .orElseThrow(() -> new RuntimeException("Admin not found"));
        return ResponseEntity.ok(admin);
    }

    @Operation(
            summary = "Create a new admin",
            description = "Creates a new admin account."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Admin created successfully",
                    content = @Content(schema = @Schema(implementation = AdminCreationRequest.class))
            )
    })
    @PostMapping
    public ResponseEntity<Admin> createAdmin(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Creating an admin",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = AdminCreationRequest.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                              "admin": {
                                                "role": "System Administrator"
                                              },
                                              "user": {
                                                "username": "Hello World",
                                                "firstName": "World",
                                                "lastName": "World",
                                                "email": "hello.world@example.com",
                                                "password": "securePassword123",
                                                "type": "A"
                                              }
                                            }
                                            """
                            )
                    )
            )
            @RequestBody AdminCreationRequest request) {
        Admin createdAdmin = adminService.createAdmin(request.admin(), request.user());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAdmin);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Admin> updateAdmin(@PathVariable Integer id, @RequestBody Admin adminDetails) {
        Admin updatedAdmin = adminService.updateAdmin(id, adminDetails);
        return ResponseEntity.ok(updatedAdmin);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdmin(@PathVariable Integer id) {
        adminService.deleteAdmin(id);
        return ResponseEntity.noContent().build();
    }

    // Request records
    public record AdminCreationRequest(Admin admin, User user) {
    }
}