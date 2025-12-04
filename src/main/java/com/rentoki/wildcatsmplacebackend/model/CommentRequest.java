package com.rentoki.wildcatsmplacebackend.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommentRequest {
    private Integer resourceId;
    private String commentText;
    private Integer parentCommentId; // null for top-level comments, set for replies
}