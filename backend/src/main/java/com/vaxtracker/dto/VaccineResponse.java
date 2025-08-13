package com.vaxtracker.dto;

import java.time.LocalDateTime;

public class VaccineResponse {
    private Long id;
    private String name;
    private String description;
    private String importance;
    private String sideEffects;
    private Integer ageInMonths;
    private Boolean isMandatory;
    private LocalDateTime createdAt;

    // Constructors
    public VaccineResponse() {}

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
}
