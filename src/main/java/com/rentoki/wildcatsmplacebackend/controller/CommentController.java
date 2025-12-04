package com.rentoki.wildcatsmplacebackend.controller;

import com.rentoki.wildcatsmplacebackend.model.CommentRequest;
import com.rentoki.wildcatsmplacebackend.model.CommentResponse;
import com.rentoki.wildcatsmplacebackend.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{studentId}")
    public ResponseEntity<CommentResponse> addComment(
            @PathVariable Integer studentId,
            @RequestBody CommentRequest request) {
        CommentResponse comment = commentService.addComment(studentId, request);
        return ResponseEntity.ok(comment);
    }

    @GetMapping("/resource/{resourceId}")
    public ResponseEntity<List<CommentResponse>> getCommentsByResource(
            @PathVariable Integer resourceId) {
        List<CommentResponse> comments = commentService.getCommentsByResource(resourceId);
        return ResponseEntity.ok(comments);
    }
}