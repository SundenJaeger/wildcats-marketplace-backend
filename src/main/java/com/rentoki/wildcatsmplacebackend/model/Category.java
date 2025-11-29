package com.rentoki.wildcatsmplacebackend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Integer categoryId;

    @Column(name = "category_name", nullable = false)
    private String categoryName;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "parent_category_id")
    private Category parentCategory;

    @JsonManagedReference
    @OneToMany(mappedBy = "parentCategory")
    private List<Category> subcategories;

    @Column(name = "description")
    private String description;

    @Column(name = "is_active")
    private Boolean isActive = true;

//    @JsonIgnore
//    @ManyToMany(mappedBy = "interests")
//    private List<Student> interestedStudents;

    @JsonIgnore
    @OneToMany(mappedBy = "category")
    private List<Resource> resources;
}