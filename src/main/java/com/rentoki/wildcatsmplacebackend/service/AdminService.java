package com.rentoki.wildcatsmplacebackend.service;

import com.rentoki.wildcatsmplacebackend.exceptions.ErrorMessages;
import com.rentoki.wildcatsmplacebackend.exceptions.AdminNotFoundException;
import com.rentoki.wildcatsmplacebackend.model.Admin;
import com.rentoki.wildcatsmplacebackend.model.User;
import com.rentoki.wildcatsmplacebackend.repository.AdminRepository;
import com.rentoki.wildcatsmplacebackend.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class AdminService {
    private final AdminRepository adminRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    public AdminService(AdminRepository adminRepository, UserRepository userRepository, UserService userService) {
        this.adminRepository = adminRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    public Optional<Admin> getAdminById(Integer id) {
        return adminRepository.findById(id);
    }

    @Transactional
    public Admin createAdmin(Admin admin, User user) {
        user.setType("A");
        User savedUser = userService.createUser(user);

        admin.setAdminId(savedUser.getUserId());
        admin.setUser(savedUser);

        return adminRepository.save(admin);
    }

    public Admin updateAdmin(Integer id, Admin adminDetails) {
        Admin admin = adminRepository.findById(id)
                .orElseThrow(() -> new AdminNotFoundException(ErrorMessages.ADMIN_NOT_FOUND.getMessage()));

        admin.setDepartment(adminDetails.getDepartment());
        admin.setRole(adminDetails.getRole());

        return adminRepository.save(admin);
    }

    public void deleteAdmin(Integer id) {
        Admin admin = adminRepository.findById(id)
                .orElseThrow(() -> new AdminNotFoundException(ErrorMessages.ADMIN_NOT_FOUND.getMessage()));
        adminRepository.delete(admin);
    }

    public List<Admin> getAdminsByDepartment(String department) {
        return adminRepository.findByDepartment(department);
    }
}