package com.rentoki.wildcatsmplacebackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "resources")
public class Resource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "resource_id")
    private Integer resourceId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    @Column(name = "item_condition", nullable = false)
    private ItemCondition condition;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ResourceStatus status = ResourceStatus.AVAILABLE;

    @Column(name = "date_posted")
    private LocalDateTime datePosted = LocalDateTime.now();

    @Column(name = "date_sold")
    private LocalDateTime dateSold;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    // CASCADE ALL - will delete images when resource is deleted
    @OneToMany(mappedBy = "resource", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ResourceImage> images;

    // CASCADE ALL - will delete reports when resource is deleted
    @OneToMany(mappedBy = "resource", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Report> reports;

    // CASCADE ALL - will delete bookmarks when resource is deleted
    @JsonIgnore
    @OneToMany(mappedBy = "resource", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Bookmark> bookmarks;

    // CASCADE ALL - will delete comments (and their notifications) when resource is deleted
    @OneToMany(mappedBy = "resource", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;

    public enum ItemCondition {
        NEW, LIKE_NEW, GOOD, FAIR, POOR
    }

    public enum ResourceStatus {
        AVAILABLE, SOLD, REMOVED
    }
}