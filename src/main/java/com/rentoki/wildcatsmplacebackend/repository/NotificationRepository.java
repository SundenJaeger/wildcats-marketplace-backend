package com.rentoki.wildcatsmplacebackend.repository;

import com.rentoki.wildcatsmplacebackend.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {

    // Find all notifications for a recipient, ordered by newest first
    List<Notification> findByRecipient_StudentIdOrderByCreatedAtDesc(Integer recipientId);

    // Find unread notifications for a recipient
    List<Notification> findByRecipient_StudentIdAndIsReadFalseOrderByCreatedAtDesc(Integer recipientId);

    // Count unread notifications for a recipient
    Long countByRecipient_StudentIdAndIsReadFalse(Integer recipientId);
}