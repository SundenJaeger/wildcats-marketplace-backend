package com.rentoki.wildcatsmplacebackend.repository;

import com.rentoki.wildcatsmplacebackend.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    List<Student> findByIsVerified(boolean isVerified);
}
