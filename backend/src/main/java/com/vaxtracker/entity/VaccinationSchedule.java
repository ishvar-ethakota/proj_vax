package com.vaxtracker.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "vaccination_schedules")
public class VaccinationSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "child_id", nullable = false)
    private Child child;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vaccine_id", nullable = false)
    private Vaccine vaccine;

    @NotNull
    private LocalDate dueDate;

    @Enumerated(EnumType.STRING)
    private Status status = Status.PENDING;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<VaccinationRecord> vaccinationRecords;

    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Reminder> reminders;

    // Constructors
    public VaccinationSchedule() {}

    public VaccinationSchedule(Child child, Vaccine vaccine, LocalDate dueDate) {
        this.child = child;
        this.vaccine = vaccine;
        this.dueDate = dueDate;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Child getChild() { return child; }
    public void setChild(Child child) { this.child = child; }

    public Vaccine getVaccine() { return vaccine; }
    public void setVaccine(Vaccine vaccine) { this.vaccine = vaccine; }

    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public List<VaccinationRecord> getVaccinationRecords() { return vaccinationRecords; }
    public void setVaccinationRecords(List<VaccinationRecord> vaccinationRecords) { this.vaccinationRecords = vaccinationRecords; }

    public List<Reminder> getReminders() { return reminders; }
    public void setReminders(List<Reminder> reminders) { this.reminders = reminders; }

    public enum Status {
        PENDING, COMPLETED, OVERDUE, SKIPPED
    }
}
