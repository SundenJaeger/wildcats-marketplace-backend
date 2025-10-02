package com.rentoki.wildcatsmplacebackend.repository;

import com.rentoki.wildcatsmplacebackend.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    Optional<Student> findByUserUsername(String username);

    Optional<Student> findByUserEmail(String email);

    List<Student> findByIsVerified(Boolean isVerified);

    List<Student> findByEnrollmentStatus(String enrollmentStatus);

    List<Student> findByProgram(String program);
}