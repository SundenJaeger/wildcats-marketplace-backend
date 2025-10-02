package com.rentoki.wildcatsmplacebackend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "admin")
public class Admin {

    @Id
    @Column(name = "admin_id")
    private Integer adminId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "admin_id")
    private User user;

    @Column(name = "department")
    private String department;

    @Column(name = "role")
    private String role;

    @OneToMany(mappedBy = "admin")
    private List<VerificationRequest> verificationRequests;

    @OneToMany(mappedBy = "admin")
    private List<Report> reports;
}