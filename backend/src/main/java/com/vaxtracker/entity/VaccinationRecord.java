package com.vaxtracker.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "vaccination_records")
public class VaccinationRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id", nullable = false)
    private VaccinationSchedule schedule;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id")
    private User doctor;

    @NotNull
    private LocalDate vaccinationDate;

    @Size(max = 100)
    private String batchNumber;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    // Constructors
    public VaccinationRecord() {}

    public VaccinationRecord(VaccinationSchedule schedule, User doctor, LocalDate vaccinationDate, String batchNumber, String notes) {
        this.schedule = schedule;
        this.doctor = doctor;
        this.vaccinationDate = vaccinationDate;
        this.batchNumber = batchNumber;
        this.notes = notes;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public VaccinationSchedule getSchedule() { return schedule; }
    public void setSchedule(VaccinationSchedule schedule) { this.schedule = schedule; }

    public User getDoctor() { return doctor; }
    public void setDoctor(User doctor) { this.doctor = doctor; }

    public LocalDate getVaccinationDate() { return vaccinationDate; }
    public void setVaccinationDate(LocalDate vaccinationDate) { this.vaccinationDate = vaccinationDate; }

    public String getBatchNumber() { return batchNumber; }
    public void setBatchNumber(String batchNumber) { this.batchNumber = batchNumber; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
