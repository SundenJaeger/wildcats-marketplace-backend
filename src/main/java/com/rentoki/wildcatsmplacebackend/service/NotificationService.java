package com.rentoki.wildcatsmplacebackend.service;

import com.rentoki.wildcatsmplacebackend.model.*;
import com.rentoki.wildcatsmplacebackend.repository.NotificationRepository;
import com.rentoki.wildcatsmplacebackend.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final StudentRepository studentRepository;

    @Transactional
    public Notification createCommentNotification(Student sender, Resource resource, Comment comment) {
        // Don't notify if commenting on own product
        if (sender.getStudentId().equals(resource.getStudent().getStudentId())) {
            return null;
        }

        Notification notification = new Notification();
        notification.setRecipient(resource.getStudent());
        notification.setSender(sender);
        notification.setResource(resource);
        notification.setComment(comment);
        notification.setType(Notification.NotificationType.COMMENT_ON_PRODUCT);
        notification.setMessage(sender.getUser().getUsername() + " commented on your product: " + resource.getTitle());

        return notificationRepository.save(notification);
    }

    @Transactional
    public Notification createReplyNotification(Student sender, Comment parentComment, Comment replyComment) {
        // Don't notify if replying to own comment
        if (sender.getStudentId().equals(parentComment.getStudent().getStudentId())) {
            return null;
        }

        Notification notification = new Notification();
        notification.setRecipient(parentComment.getStudent());
        notification.setSender(sender);
        notification.setResource(parentComment.getResource());
        notification.setComment(replyComment);
        notification.setType(Notification.NotificationType.REPLY_TO_COMMENT);
        notification.setMessage(sender.getUser().getUsername() + " replied to your comment");

        return notificationRepository.save(notification);
    }

    public List<Notification> getNotificationsForStudent(Integer studentId) {
        return notificationRepository.findByRecipient_StudentIdOrderByCreatedAtDesc(studentId);
    }

    public List<Notification> getUnreadNotificationsForStudent(Integer studentId) {
        return notificationRepository.findByRecipient_StudentIdAndIsReadFalseOrderByCreatedAtDesc(studentId);
    }

    public Long getUnreadCount(Integer studentId) {
        return notificationRepository.countByRecipient_StudentIdAndIsReadFalse(studentId);
    }

    @Transactional
    public void markAsRead(Integer notificationId) {
        notificationRepository.findById(notificationId).ifPresent(notification -> {
            notification.setIsRead(true);
            notificationRepository.save(notification);
        });
    }

    @Transactional
    public void markAllAsRead(Integer studentId) {
        List<Notification> notifications = notificationRepository
                .findByRecipient_StudentIdAndIsReadFalseOrderByCreatedAtDesc(studentId);

        notifications.forEach(notification -> notification.setIsRead(true));
        notificationRepository.saveAll(notifications);
    }

    @Transactional
    public void deleteNotification(Integer notificationId) {
        notificationRepository.deleteById(notificationId);
    }
}