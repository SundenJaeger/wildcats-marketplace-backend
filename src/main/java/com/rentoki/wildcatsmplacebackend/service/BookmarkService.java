package com.rentoki.wildcatsmplacebackend.service;

import com.rentoki.wildcatsmplacebackend.model.BookmarkResponse;
import com.rentoki.wildcatsmplacebackend.model.BookmarkRequest;
import com.rentoki.wildcatsmplacebackend.model.Bookmark;
import com.rentoki.wildcatsmplacebackend.model.Resource;
import com.rentoki.wildcatsmplacebackend.model.Student;
import com.rentoki.wildcatsmplacebackend.repository.BookmarkRepository;
import com.rentoki.wildcatsmplacebackend.repository.ResourceRepository;
import com.rentoki.wildcatsmplacebackend.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final StudentRepository studentRepository;
    private final ResourceRepository resourceRepository;

    public List<BookmarkResponse> getStudentBookmarks(Integer studentId) {
        return bookmarkRepository.findByStudentStudentId(studentId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public BookmarkResponse addBookmark(Integer studentId, BookmarkRequest request) {
        if (bookmarkRepository.existsByStudentStudentIdAndResourceResourceId(
                studentId, request.getResourceId())) {
            throw new RuntimeException("Resource already bookmarked");
        }

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Resource resource = resourceRepository.findById(request.getResourceId())
                .orElseThrow(() -> new RuntimeException("Resource not found"));

        Bookmark bookmark = new Bookmark();
        bookmark.setStudent(student);
        bookmark.setResource(resource);

        Bookmark saved = bookmarkRepository.save(bookmark);
        return convertToDTO(saved);
    }

    @Transactional
    public void removeBookmark(Integer studentId, Integer resourceId) {
        bookmarkRepository.deleteByStudentStudentIdAndResourceResourceId(
                studentId, resourceId);
    }

    public boolean isBookmarked(Integer studentId, Integer resourceId) {
        return bookmarkRepository.existsByStudentStudentIdAndResourceResourceId(
                studentId, resourceId);
    }

    private BookmarkResponse convertToDTO(Bookmark bookmark) {
        BookmarkResponse dto = new BookmarkResponse();
        dto.setBookmarkId(bookmark.getBookmarkId());
        dto.setStudentId(bookmark.getStudent().getStudentId());
        dto.setResourceId(bookmark.getResource().getResourceId());
        dto.setResourceTitle(bookmark.getResource().getTitle());
        dto.setResourceDescription(bookmark.getResource().getDescription());
        dto.setResourcePrice(bookmark.getResource().getPrice().toString());
        dto.setResourceStatus(bookmark.getResource().getStatus().toString());
        dto.setDateSaved(bookmark.getDateSaved());
        return dto;
    }
}