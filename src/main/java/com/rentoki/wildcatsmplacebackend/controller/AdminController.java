package com.rentoki.wildcatsmplacebackend.controller;

import com.rentoki.wildcatsmplacebackend.model.Admin;
import com.rentoki.wildcatsmplacebackend.model.User;
import com.rentoki.wildcatsmplacebackend.service.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admins")
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

    @PostMapping
    public ResponseEntity<Admin> createAdmin(@RequestBody AdminCreationRequest request) {
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

    @GetMapping("/department/{department}")
    public ResponseEntity<List<Admin>> getAdminsByDepartment(@PathVariable String department) {
        List<Admin> admins = adminService.getAdminsByDepartment(department);
        return ResponseEntity.ok(admins);
    }

    // Request records
    public record AdminCreationRequest(Admin admin, User user) {}
}