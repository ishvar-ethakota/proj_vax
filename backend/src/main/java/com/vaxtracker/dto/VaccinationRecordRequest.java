package com.vaxtracker.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class VaccinationRecordRequest {
    @NotNull
    private LocalDate vaccinationDate;

    @Size(max = 100)
    private String batchNumber;

    @Size(max = 1000)
    private String notes;

    // Constructors
    public VaccinationRecordRequest() {}

    public VaccinationRecordRequest(LocalDate vaccinationDate, String batchNumber, String notes) {
        this.vaccinationDate = vaccinationDate;
        this.batchNumber = batchNumber;
        this.notes = notes;
    }

    // Getters and Setters
    public LocalDate getVaccinationDate() { return vaccinationDate; }
    public void setVaccinationDate(LocalDate vaccinationDate) { this.vaccinationDate = vaccinationDate; }

    public String getBatchNumber() { return batchNumber; }
    public void setBatchNumber(String batchNumber) { this.batchNumber = batchNumber; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}
