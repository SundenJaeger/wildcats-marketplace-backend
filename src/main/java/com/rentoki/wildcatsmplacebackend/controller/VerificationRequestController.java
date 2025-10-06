package com.rentoki.wildcatsmplacebackend.controller;

import com.rentoki.wildcatsmplacebackend.model.VerificationRequest;
import com.rentoki.wildcatsmplacebackend.service.VerificationRequestService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/verification-requests")
@Tag(name = "5. Verification", description = "Student verification request APIs")
public class VerificationRequestController {
    private final VerificationRequestService verificationRequestService;

    public VerificationRequestController(VerificationRequestService verificationRequestService) {
        this.verificationRequestService = verificationRequestService;
    }

    @GetMapping
    public ResponseEntity<List<VerificationRequest>> getAllVerificationRequests() {
        List<VerificationRequest> requests = verificationRequestService.getAllVerificationRequests();
        return ResponseEntity.ok(requests);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<VerificationRequest>> getVerificationRequestsByStatus(
            @PathVariable VerificationRequest.VerificationStatus status) {
        List<VerificationRequest> requests = verificationRequestService.getVerificationRequestsByStatus(status);
        return ResponseEntity.ok(requests);
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<VerificationRequest>> getVerificationRequestsByStudent(@PathVariable Integer studentId) {
        List<VerificationRequest> requests = verificationRequestService.getVerificationRequestsByStudent(studentId);
        return ResponseEntity.ok(requests);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VerificationRequest> getVerificationRequestById(@PathVariable Integer id) {
        VerificationRequest request = verificationRequestService.getVerificationRequestById(id);
        return ResponseEntity.ok(request);
    }

    @PostMapping
    public ResponseEntity<VerificationRequest> createVerificationRequest(@RequestBody VerificationRequest verificationRequest) {
        VerificationRequest createdRequest = verificationRequestService.createVerificationRequest(verificationRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRequest);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<VerificationRequest> updateVerificationRequestStatus(
            @PathVariable Integer id,
            @RequestBody StatusUpdateRequest request) {
        VerificationRequest updatedRequest = verificationRequestService.updateVerificationRequestStatus(
                id, request.status(), request.adminNotes(), request.rejectionReason());
        return ResponseEntity.ok(updatedRequest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVerificationRequest(@PathVariable Integer id) {
        verificationRequestService.deleteVerificationRequest(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/student/{studentId}/pending")
    public ResponseEntity<VerificationRequest> getPendingRequestByStudent(@PathVariable Integer studentId) {
        Optional<VerificationRequest> request = verificationRequestService.getPendingRequestByStudent(studentId);
        return request.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Request records
    public record StatusUpdateRequest(
            VerificationRequest.VerificationStatus status,
            String adminNotes,
            String rejectionReason
    ) {}
}