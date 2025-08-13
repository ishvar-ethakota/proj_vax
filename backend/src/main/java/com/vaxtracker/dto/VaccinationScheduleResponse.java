package com.vaxtracker.dto;

import com.vaxtracker.entity.VaccinationSchedule;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class VaccinationScheduleResponse {
    private Long id;
    private Long childId;
    private String childName;
    private Long vaccineId;
    private String vaccineName;
    private String vaccineDescription;
    private LocalDate dueDate;
    private VaccinationSchedule.Status status;
    private LocalDateTime createdAt;
    private boolean isOverdue;

    // Constructors
    public VaccinationScheduleResponse() {}

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getChildId() { return childId; }
    public void setChildId(Long childId) { this.childId = childId; }

    public String getChildName() { return childName; }
    public void setChildName(String childName) { this.childName = childName; }

    public Long getVaccineId() { return vaccineId; }
    public void setVaccineId(Long vaccineId) { this.vaccineId = vaccineId; }

    public String getVaccineName() { return vaccineName; }
    public void setVaccineName(String vaccineName) { this.vaccineName = vaccineName; }

    public String getVaccineDescription() { return vaccineDescription; }
    public void setVaccineDescription(String vaccineDescription) { this.vaccineDescription = vaccineDescription; }

    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }

    public VaccinationSchedule.Status getStatus() { return status; }
    public void setStatus(VaccinationSchedule.Status status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public boolean isOverdue() { return isOverdue; }
    public void setOverdue(boolean overdue) { isOverdue = overdue; }
}
