package com.vaxtracker.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "vaccines")
public class Vaccine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 200)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String importance;

    @Column(columnDefinition = "TEXT")
    private String sideEffects;

    @NotNull
    private Integer ageInMonths;

    private Boolean isMandatory = true;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    // Constructors
    public Vaccine() {}

    public Vaccine(String name, String description, String importance, String sideEffects, Integer ageInMonths, Boolean isMandatory) {
        this.name = name;
        this.description = description;
        this.importance = importance;
        this.sideEffects = sideEffects;
        this.ageInMonths = ageInMonths;
        this.isMandatory = isMandatory;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getImportance() { return importance; }
    public void setImportance(String importance) { this.importance = importance; }

    public String getSideEffects() { return sideEffects; }
    public void setSideEffects(String sideEffects) { this.sideEffects = sideEffects; }

    public Integer getAgeInMonths() { return ageInMonths; }
    public void setAgeInMonths(Integer ageInMonths) { this.ageInMonths = ageInMonths; }

    public Boolean getIsMandatory() { return isMandatory; }
    public void setIsMandatory(Boolean isMandatory) { this.isMandatory = isMandatory; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
