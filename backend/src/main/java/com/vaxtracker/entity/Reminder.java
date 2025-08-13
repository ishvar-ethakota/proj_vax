package com.vaxtracker.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "reminders")
public class Reminder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id", nullable = false)
    private VaccinationSchedule schedule;

    @NotNull
    private LocalDate reminderDate;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String message;

    @Enumerated(EnumType.STRING)
    private Status status = Status.PENDING;

    private LocalDateTime sentAt;

    @CreationTimestamp
    private LocalDateTime createdAt;

    // Constructors
    public Reminder() {}

    public Reminder(VaccinationSchedule schedule, LocalDate reminderDate, String message) {
        this.schedule = schedule;
        this.reminderDate = reminderDate;
        this.message = message;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public VaccinationSchedule getSchedule() { return schedule; }
    public void setSchedule(VaccinationSchedule schedule) { this.schedule = schedule; }

    public LocalDate getReminderDate() { return reminderDate; }
    public void setReminderDate(LocalDate reminderDate) { this.reminderDate = reminderDate; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    public LocalDateTime getSentAt() { return sentAt; }
    public void setSentAt(LocalDateTime sentAt) { this.sentAt = sentAt; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public enum Status {
        PENDING, SENT, FAILED
    }
}
