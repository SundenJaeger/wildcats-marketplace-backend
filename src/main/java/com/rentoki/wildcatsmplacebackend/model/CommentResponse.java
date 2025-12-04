package com.rentoki.wildcatsmplacebackend.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class CommentResponse {
    private Integer commentId;
    private String commentText;
    private LocalDateTime timestamp;

    // Student info
    private Integer studentId;
    private String studentName; // firstName + lastName
    private String studentUsername;

    // Resource info
    private Integer resourceId;

    // Parent comment info (for replies)
    private Integer parentCommentId;

    // Nested replies
    private List<CommentResponse> replies;
}