package com.rentoki.wildcatsmplacebackend.controller;

import com.rentoki.wildcatsmplacebackend.model.BookmarkResponse;
import com.rentoki.wildcatsmplacebackend.model.BookmarkRequest;
import com.rentoki.wildcatsmplacebackend.service.BookmarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookmarks")
@RequiredArgsConstructor
public class BookmarkController {

    private final BookmarkService bookmarkService;

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<BookmarkResponse>> getStudentBookmarks(
            @PathVariable Integer studentId) {
        return ResponseEntity.ok(bookmarkService.getStudentBookmarks(studentId));
    }

    @PostMapping("/student/{studentId}")
    public ResponseEntity<BookmarkResponse> addBookmark(
            @PathVariable Integer studentId,
            @RequestBody BookmarkRequest request) {
        return ResponseEntity.ok(bookmarkService.addBookmark(studentId, request));
    }

    @DeleteMapping("/student/{studentId}/resource/{resourceId}")
    public ResponseEntity<Void> removeBookmark(
            @PathVariable Integer studentId,
            @PathVariable Integer resourceId) {
        bookmarkService.removeBookmark(studentId, resourceId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/student/{studentId}/resource/{resourceId}/exists")
    public ResponseEntity<Boolean> isBookmarked(
            @PathVariable Integer studentId,
            @PathVariable Integer resourceId) {
        return ResponseEntity.ok(
                bookmarkService.isBookmarked(studentId, resourceId)
        );
    }
}