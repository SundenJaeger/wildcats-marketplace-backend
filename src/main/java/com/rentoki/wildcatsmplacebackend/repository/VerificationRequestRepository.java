package com.rentoki.wildcatsmplacebackend.repository;

import com.rentoki.wildcatsmplacebackend.model.VerificationRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface VerificationRequestRepository extends JpaRepository<VerificationRequest, Integer> {
    List<VerificationRequest> findByStudentStudentId(Integer studentId);
    List<VerificationRequest> findByStatus(VerificationRequest.VerificationStatus status);
    Optional<VerificationRequest> findByStudentStudentIdAndStatus(Integer studentId, VerificationRequest.VerificationStatus status);
}