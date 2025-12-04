package com.rentoki.wildcatsmplacebackend.repository;

import com.rentoki.wildcatsmplacebackend.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findByResourceResourceIdAndParentCommentIsNullOrderByTimestampDesc(Integer resourceId);
    List<Comment> findByParentCommentCommentIdOrderByTimestampAsc(Integer parentCommentId);
}