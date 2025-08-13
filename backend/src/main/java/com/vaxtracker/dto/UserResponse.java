package com.vaxtracker.dto;

import com.vaxtracker.entity.User;

import java.time.LocalDateTime;

public class UserResponse {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private User.Role role;
    private String preferredLanguage;
    private LocalDateTime createdAt;

    // Constructors
    public UserResponse() {}

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public User.Role getRole() { return role; }
    public void setRole(User.Role role) { this.role = role; }

    public String getPreferredLanguage() { return preferredLanguage; }
    public void setPreferredLanguage(String preferredLanguage) { this.preferredLanguage = preferredLanguage; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
