package com.vaxtracker.dto;

import com.vaxtracker.entity.Child;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ChildResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private Child.Gender gender;
    private Child.BloodGroup bloodGroup;
    private String photoUrl;
    private LocalDateTime createdAt;
    private Integer ageInMonths;

    // Constructors
    public ChildResponse() {}

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    public Child.Gender getGender() { return gender; }
    public void setGender(Child.Gender gender) { this.gender = gender; }

    public Child.BloodGroup getBloodGroup() { return bloodGroup; }
    public void setBloodGroup(Child.BloodGroup bloodGroup) { this.bloodGroup = bloodGroup; }

    public String getPhotoUrl() { return photoUrl; }
    public void setPhotoUrl(String photoUrl) { this.photoUrl = photoUrl; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public Integer getAgeInMonths() { return ageInMonths; }
    public void setAgeInMonths(Integer ageInMonths) { this.ageInMonths = ageInMonths; }
}
