package com.vaxtracker.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class VaccinationRecordResponse {
    private Long id;
    private Long scheduleId;
    private String doctorName;
    private String childName;
    private String vaccineName;
    private LocalDate vaccinationDate;
    private String batchNumber;
    private String notes;
    private LocalDateTime createdAt;

    // Constructors
    public VaccinationRecordResponse() {}

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getScheduleId() { return scheduleId; }
    public void setScheduleId(Long scheduleId) { this.scheduleId = scheduleId; }

    public String getDoctorName() { return doctorName; }
    public void setDoctorName(String doctorName) { this.doctorName = doctorName; }

    public String getChildName() { return childName; }
    public void setChildName(String childName) { this.childName = childName; }

    public String getVaccineName() { return vaccineName; }
    public void setVaccineName(String vaccineName) { this.vaccineName = vaccineName; }

    public LocalDate getVaccinationDate() { return vaccinationDate; }
    public void setVaccinationDate(LocalDate vaccinationDate) { this.vaccinationDate = vaccinationDate; }

    public String getBatchNumber() { return batchNumber; }
    public void setBatchNumber(String batchNumber) { this.batchNumber = batchNumber; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
