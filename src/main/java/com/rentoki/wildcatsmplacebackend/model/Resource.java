package com.rentoki.wildcatsmplacebackend.model;

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
    @Column(name = "condition", nullable = false)
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

    @OneToMany(mappedBy = "resource", cascade = CascadeType.ALL)
    private List<ResourceImage> images;

    @OneToMany(mappedBy = "resource")
    private List<Report> reports;

    public enum ItemCondition {
        NEW, LIKE_NEW, GOOD, FAIR, POOR
    }

    public enum ResourceStatus {
        AVAILABLE, SOLD, REMOVED
    }
}