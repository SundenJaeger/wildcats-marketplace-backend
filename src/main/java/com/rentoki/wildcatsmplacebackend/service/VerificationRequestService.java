package com.rentoki.wildcatsmplacebackend.service;

import com.rentoki.wildcatsmplacebackend.exceptions.ErrorMessages;
import com.rentoki.wildcatsmplacebackend.exceptions.VerificationRequestNotFoundException;
import com.rentoki.wildcatsmplacebackend.model.VerificationRequest;
import com.rentoki.wildcatsmplacebackend.repository.VerificationRequestRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class VerificationRequestService {
    private final VerificationRequestRepository verificationRequestRepository;
    private final StudentService studentService;

    public VerificationRequestService(VerificationRequestRepository verificationRequestRepository, StudentService studentService) {
        this.verificationRequestRepository = verificationRequestRepository;
        this.studentService = studentService;
    }

    public List<VerificationRequest> getAllVerificationRequests() {
        return verificationRequestRepository.findAll();
    }

    public List<VerificationRequest> getVerificationRequestsByStatus(VerificationRequest.VerificationStatus status) {
        return verificationRequestRepository.findByStatus(status);
    }

    public List<VerificationRequest> getVerificationRequestsByStudent(Integer studentId) {
        return verificationRequestRepository.findByStudentStudentId(studentId);
    }

    public VerificationRequest getVerificationRequestById(Integer id) {
        return verificationRequestRepository.findById(id)
                .orElseThrow(() -> new VerificationRequestNotFoundException(ErrorMessages.VERIFICATION_REQUEST_NOT_FOUND.getMessage()));
    }

    public VerificationRequest createVerificationRequest(VerificationRequest verificationRequest) {
        verificationRequest.setStatus(VerificationRequest.VerificationStatus.PENDING);
        verificationRequest.setRequestDate(LocalDateTime.now());
        return verificationRequestRepository.save(verificationRequest);
    }

    public VerificationRequest updateVerificationRequestStatus(Integer id, VerificationRequest.VerificationStatus status,
                                                               String adminNotes, String rejectionReason) {
        VerificationRequest request = verificationRequestRepository.findById(id)
                .orElseThrow(() -> new VerificationRequestNotFoundException(ErrorMessages.VERIFICATION_REQUEST_NOT_FOUND.getMessage()));

        request.setStatus(status);
        request.setResponseDate(LocalDateTime.now());
        request.setAdminNotes(adminNotes);

        if (status == VerificationRequest.VerificationStatus.REJECTED) {
            request.setRejectionReason(rejectionReason);
        }

        if (status == VerificationRequest.VerificationStatus.APPROVED) {
            studentService.updateStudentVerification(request.getStudent().getStudentId(), true);
        }

        return verificationRequestRepository.save(request);
    }

    public void deleteVerificationRequest(Integer id) {
        VerificationRequest request = verificationRequestRepository.findById(id)
                .orElseThrow(() -> new VerificationRequestNotFoundException(ErrorMessages.VERIFICATION_REQUEST_NOT_FOUND.getMessage()));
        verificationRequestRepository.delete(request);
    }

    public Optional<VerificationRequest> getPendingRequestByStudent(Integer studentId) {
        return verificationRequestRepository.findByStudentStudentIdAndStatus(
                studentId, VerificationRequest.VerificationStatus.PENDING);
    }
}