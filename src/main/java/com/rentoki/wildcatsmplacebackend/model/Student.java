package com.rentoki.wildcatsmplacebackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "student")
public class Student {

    @Id
    @Column(name = "student_id")
    private Integer studentId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "student_id")
    private User user;

    @Column(name = "is_verified")
    private Boolean isVerified = false;

    @Column(name = "enrollment_status")
    private String enrollmentStatus;

    @Column(name = "program")
    private String program;

    @Column(name = "year_level")
    private String yearLevel;

//    @JsonIgnore
//    @ManyToMany
//    @JoinTable(
//            name = "student_interest",
//            joinColumns = @JoinColumn(name = "student_id"),
//            inverseJoinColumns = @JoinColumn(name = "category_id")
//    )
//    private List<Category> interests;

    @JsonIgnore
    @OneToMany(mappedBy = "student")
    private List<Resource> resources;

    @JsonIgnore
    @OneToMany(mappedBy = "student")
    private List<VerificationRequest> verificationRequests;

    @JsonIgnore
    @OneToMany(mappedBy = "student")
    private List<Report> reports;

    @JsonIgnore
    @OneToMany(mappedBy = "student")
    private List<Bookmark> bookmarks;
}