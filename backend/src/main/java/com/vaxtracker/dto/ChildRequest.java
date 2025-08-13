package com.vaxtracker.dto;

import com.vaxtracker.entity.Child;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class ChildRequest {
    @NotBlank
    @Size(max = 100)
    private String firstName;

    @NotBlank
    @Size(max = 100)
    private String lastName;

    @NotNull
    private LocalDate dateOfBirth;

    @NotNull
    private Child.Gender gender;

    @NotNull
    private Child.BloodGroup bloodGroup;

    // Constructors
    public ChildRequest() {}

    // Getters and Setters
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
}
