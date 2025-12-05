package com.rentoki.wildcatsmplacebackend.controller;

import com.rentoki.wildcatsmplacebackend.model.Notification;
import com.rentoki.wildcatsmplacebackend.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Notification>> getNotifications(@PathVariable Integer studentId) {
        List<Notification> notifications = notificationService.getNotificationsForStudent(studentId);
        return ResponseEntity.ok(notifications);
    }

    @GetMapping("/student/{studentId}/unread")
    public ResponseEntity<List<Notification>> getUnreadNotifications(@PathVariable Integer studentId) {
        List<Notification> notifications = notificationService.getUnreadNotificationsForStudent(studentId);
        return ResponseEntity.ok(notifications);
    }

    @GetMapping("/student/{studentId}/unread/count")
    public ResponseEntity<Map<String, Long>> getUnreadCount(@PathVariable Integer studentId) {
        Long count = notificationService.getUnreadCount(studentId);
        return ResponseEntity.ok(Map.of("count", count));
    }

    @PutMapping("/{notificationId}/read")
    public ResponseEntity<Void> markAsRead(@PathVariable Integer notificationId) {
        notificationService.markAsRead(notificationId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/student/{studentId}/read-all")
    public ResponseEntity<Void> markAllAsRead(@PathVariable Integer studentId) {
        notificationService.markAllAsRead(studentId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{notificationId}")
    public ResponseEntity<Void> deleteNotification(@PathVariable Integer notificationId) {
        notificationService.deleteNotification(notificationId);
        return ResponseEntity.ok().build();
    }
}