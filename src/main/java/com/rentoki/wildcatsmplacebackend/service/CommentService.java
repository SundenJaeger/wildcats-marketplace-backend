package com.rentoki.wildcatsmplacebackend.service;

import com.rentoki.wildcatsmplacebackend.exceptions.ResourceNotFoundException;
import com.rentoki.wildcatsmplacebackend.exceptions.StudentNotFoundException;
import com.rentoki.wildcatsmplacebackend.model.*;
import com.rentoki.wildcatsmplacebackend.repository.CommentRepository;
import com.rentoki.wildcatsmplacebackend.repository.ResourceRepository;
import com.rentoki.wildcatsmplacebackend.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ResourceRepository resourceRepository;
    private final StudentRepository studentRepository;
    private final NotificationService notificationService;

    @Transactional
    public CommentResponse addComment(Integer studentId, CommentRequest request) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException("Student not found"));

        Resource resource = resourceRepository.findById(request.getResourceId())
                .orElseThrow(() -> new ResourceNotFoundException("Resource not found"));

        Comment comment = new Comment();
        comment.setCommentText(request.getCommentText());
        comment.setResource(resource);
        comment.setStudent(student);
        comment.setTimestamp(LocalDateTime.now());

        // Handle reply
        Comment parentComment = null;
        if (request.getParentCommentId() != null) {
            parentComment = commentRepository.findById(request.getParentCommentId())
                    .orElseThrow(() -> new RuntimeException("Parent comment not found"));
            comment.setParentComment(parentComment);
        }

        Comment saved = commentRepository.save(comment);

        // Create notifications after saving comment
        if (parentComment != null) {
            // Notify the parent comment's author about the reply
            notificationService.createReplyNotification(student, parentComment, saved);
        } else {
            // Notify the product owner about the comment
            notificationService.createCommentNotification(student, resource, saved);
        }

        return convertToDTO(saved, false); // Don't load replies for a newly created comment
    }

    public List<CommentResponse> getCommentsByResource(Integer resourceId) {
        List<Comment> topLevelComments = commentRepository
                .findByResourceResourceIdAndParentCommentIsNullOrderByTimestampDesc(resourceId);

        return topLevelComments.stream()
                .map(comment -> convertToDTO(comment, true)) // Load replies
                .collect(Collectors.toList());
    }

    private CommentResponse convertToDTO(Comment comment, boolean includeReplies) {
        CommentResponse dto = new CommentResponse();
        dto.setCommentId(comment.getCommentId());
        dto.setCommentText(comment.getCommentText());
        dto.setTimestamp(comment.getTimestamp());

        // Student info
        dto.setStudentId(comment.getStudent().getStudentId());
        dto.setStudentName(comment.getStudent().getUser().getFirstName() + " " +
                comment.getStudent().getUser().getLastName());
        dto.setStudentUsername(comment.getStudent().getUser().getUsername());

        // Resource info
        dto.setResourceId(comment.getResource().getResourceId());

        // Parent comment info
        if (comment.getParentComment() != null) {
            dto.setParentCommentId(comment.getParentComment().getCommentId());
        }

        // Load replies if requested
        if (includeReplies && comment.getReplies() != null && !comment.getReplies().isEmpty()) {
            dto.setReplies(comment.getReplies().stream()
                    .map(reply -> convertToDTO(reply, false)) // Don't recursively load nested replies
                    .collect(Collectors.toList()));
        }

        return dto;
    }
}