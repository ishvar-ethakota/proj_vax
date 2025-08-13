package com.vaxtracker.controller;

import com.vaxtracker.dto.ChildResponse;
import com.vaxtracker.dto.VaccinationRecordRequest;
import com.vaxtracker.dto.VaccinationRecordResponse;
import com.vaxtracker.dto.VaccinationScheduleResponse;
import com.vaxtracker.service.DoctorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctor")
@CrossOrigin(origins = "*")
@PreAuthorize("hasRole('DOCTOR')")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @GetMapping("/patients")
    public ResponseEntity<List<ChildResponse>> getPatients(Authentication authentication) {
        List<ChildResponse> patients = doctorService.getPatients(authentication.getName());
        return ResponseEntity.ok(patients);
    }

    @GetMapping("/patients/{childId}/schedule")
    public ResponseEntity<List<VaccinationScheduleResponse>> getPatientSchedule(
            @PathVariable Long childId, Authentication authentication) {
        List<VaccinationScheduleResponse> schedule = doctorService.getPatientSchedule(childId, authentication.getName());
        return ResponseEntity.ok(schedule);
    }

    @GetMapping("/schedules/pending")
    public ResponseEntity<List<VaccinationScheduleResponse>> getPendingVaccinations(Authentication authentication) {
        List<VaccinationScheduleResponse> pending = doctorService.getPendingVaccinations(authentication.getName());
        return ResponseEntity.ok(pending);
    }

    @GetMapping("/schedules/overdue")
    public ResponseEntity<List<VaccinationScheduleResponse>> getOverdueVaccinations(Authentication authentication) {
        List<VaccinationScheduleResponse> overdue = doctorService.getOverdueVaccinations(authentication.getName());
        return ResponseEntity.ok(overdue);
    }

    @PostMapping("/schedules/{scheduleId}/complete")
    public ResponseEntity<VaccinationRecordResponse> completeVaccination(
            @PathVariable Long scheduleId,
            @Valid @RequestBody VaccinationRecordRequest request,
            Authentication authentication) {
        VaccinationRecordResponse record = doctorService.completeVaccination(scheduleId, request, authentication.getName());
        return ResponseEntity.ok(record);
    }

    @PutMapping("/schedules/{scheduleId}/status")
    public ResponseEntity<String> updateVaccinationStatus(
            @PathVariable Long scheduleId,
            @RequestParam String status,
            Authentication authentication) {
        doctorService.updateVaccinationStatus(scheduleId, status, authentication.getName());
        return ResponseEntity.ok("Status updated successfully");
    }

    @GetMapping("/records")
    public ResponseEntity<List<VaccinationRecordResponse>> getVaccinationRecords(Authentication authentication) {
        List<VaccinationRecordResponse> records = doctorService.getVaccinationRecords(authentication.getName());
        return ResponseEntity.ok(records);
    }

    @PostMapping("/patients/{childId}/assign")
    public ResponseEntity<String> assignPatient(@PathVariable Long childId, Authentication authentication) {
        doctorService.assignPatient(childId, authentication.getName());
        return ResponseEntity.ok("Patient assigned successfully");
    }
}
