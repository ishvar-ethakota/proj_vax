package com.vaxtracker.service;

import com.vaxtracker.dto.*;
import com.vaxtracker.entity.*;
import com.vaxtracker.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoctorService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChildRepository childRepository;

    @Autowired
    private VaccinationScheduleRepository scheduleRepository;

    @Autowired
    private VaccinationRecordRepository recordRepository;

    @Autowired
    private DoctorPatientRepository doctorPatientRepository;

    public List<ChildResponse> getPatients(String doctorEmail) {
        User doctor = userRepository.findByEmail(doctorEmail)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        List<DoctorPatient> assignments = doctorPatientRepository.findByDoctorAndIsActiveTrue(doctor);
        
        return assignments.stream()
                .map(assignment -> convertChildToResponse(assignment.getChild()))
                .collect(Collectors.toList());
    }

    public List<VaccinationScheduleResponse> getPatientSchedule(Long childId, String doctorEmail) {
        User doctor = userRepository.findByEmail(doctorEmail)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        Child child = childRepository.findById(childId)
                .orElseThrow(() -> new RuntimeException("Child not found"));

        // Verify doctor has access to this child
        boolean hasAccess = doctorPatientRepository.existsByDoctorAndChildAndIsActiveTrue(doctor, child);
        if (!hasAccess) {
            throw new RuntimeException("Access denied");
        }

        List<VaccinationSchedule> schedules = scheduleRepository.findByChildId(childId);
        return schedules.stream()
                .map(this::convertScheduleToResponse)
                .collect(Collectors.toList());
    }

    public List<VaccinationScheduleResponse> getPendingVaccinations(String doctorEmail) {
        User doctor = userRepository.findByEmail(doctorEmail)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        List<DoctorPatient> assignments = doctorPatientRepository.findByDoctorAndIsActiveTrue(doctor);
        List<Long> childIds = assignments.stream()
                .map(assignment -> assignment.getChild().getId())
                .collect(Collectors.toList());

        List<VaccinationSchedule> pendingSchedules = scheduleRepository.findByChildIdInAndStatus(
                childIds, VaccinationSchedule.Status.PENDING);

        return pendingSchedules.stream()
                .map(this::convertScheduleToResponse)
                .collect(Collectors.toList());
    }

    public List<VaccinationScheduleResponse> getOverdueVaccinations(String doctorEmail) {
        User doctor = userRepository.findByEmail(doctorEmail)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        List<DoctorPatient> assignments = doctorPatientRepository.findByDoctorAndIsActiveTrue(doctor);
        List<Long> childIds = assignments.stream()
                .map(assignment -> assignment.getChild().getId())
                .collect(Collectors.toList());

        List<VaccinationSchedule> overdueSchedules = scheduleRepository.findOverdueSchedulesByChildIds(
                childIds, LocalDate.now());

        return overdueSchedules.stream()
                .map(this::convertScheduleToResponse)
                .collect(Collectors.toList());
    }

    public VaccinationRecordResponse completeVaccination(Long scheduleId, VaccinationRecordRequest request, String doctorEmail) {
        User doctor = userRepository.findByEmail(doctorEmail)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        VaccinationSchedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new RuntimeException("Schedule not found"));

        // Verify doctor has access to this child
        boolean hasAccess = doctorPatientRepository.existsByDoctorAndChildAndIsActiveTrue(doctor, schedule.getChild());
        if (!hasAccess) {
            throw new RuntimeException("Access denied");
        }

        // Create vaccination record
        VaccinationRecord record = new VaccinationRecord();
        record.setSchedule(schedule);
        record.setDoctor(doctor);
        record.setVaccinationDate(request.getVaccinationDate());
        record.setBatchNumber(request.getBatchNumber());
        record.setNotes(request.getNotes());

        VaccinationRecord savedRecord = recordRepository.save(record);

        // Update schedule status
        schedule.setStatus(VaccinationSchedule.Status.COMPLETED);
        scheduleRepository.save(schedule);

        return convertRecordToResponse(savedRecord);
    }

    public void updateVaccinationStatus(Long scheduleId, String status, String doctorEmail) {
        User doctor = userRepository.findByEmail(doctorEmail)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        VaccinationSchedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new RuntimeException("Schedule not found"));

        // Verify doctor has access to this child
        boolean hasAccess = doctorPatientRepository.existsByDoctorAndChildAndIsActiveTrue(doctor, schedule.getChild());
        if (!hasAccess) {
            throw new RuntimeException("Access denied");
        }

        try {
            VaccinationSchedule.Status newStatus = VaccinationSchedule.Status.valueOf(status.toUpperCase());
            schedule.setStatus(newStatus);
            scheduleRepository.save(schedule);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid status: " + status);
        }
    }

    public List<VaccinationRecordResponse> getVaccinationRecords(String doctorEmail) {
        User doctor = userRepository.findByEmail(doctorEmail)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        List<VaccinationRecord> records = recordRepository.findByDoctorOrderByCreatedAtDesc(doctor);
        return records.stream()
                .map(this::convertRecordToResponse)
                .collect(Collectors.toList());
    }

    public void assignPatient(Long childId, String doctorEmail) {
        User doctor = userRepository.findByEmail(doctorEmail)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        Child child = childRepository.findById(childId)
                .orElseThrow(() -> new RuntimeException("Child not found"));

        // Check if assignment already exists
        if (doctorPatientRepository.existsByDoctorAndChildAndIsActiveTrue(doctor, child)) {
            throw new RuntimeException("Patient already assigned to this doctor");
        }

        DoctorPatient assignment = new DoctorPatient();
        assignment.setDoctor(doctor);
        assignment.setChild(child);
        assignment.setAssignedDate(LocalDate.now());
        assignment.setIsActive(true);

        doctorPatientRepository.save(assignment);
    }

    private ChildResponse convertChildToResponse(Child child) {
        ChildResponse response = new ChildResponse();
        response.setId(child.getId());
        response.setFirstName(child.getFirstName());
        response.setLastName(child.getLastName());
        response.setDateOfBirth(child.getDateOfBirth());
        response.setGender(child.getGender());
        response.setBloodGroup(child.getBloodGroup());
        response.setPhotoUrl(child.getPhotoUrl());
        response.setCreatedAt(child.getCreatedAt());
        
        // Calculate age in months
        java.time.Period period = java.time.Period.between(child.getDateOfBirth(), LocalDate.now());
        response.setAgeInMonths(period.getYears() * 12 + period.getMonths());
        
        return response;
    }

    private VaccinationScheduleResponse convertScheduleToResponse(VaccinationSchedule schedule) {
        VaccinationScheduleResponse response = new VaccinationScheduleResponse();
        response.setId(schedule.getId());
        response.setChildId(schedule.getChild().getId());
        response.setChildName(schedule.getChild().getFirstName() + " " + schedule.getChild().getLastName());
        response.setVaccineId(schedule.getVaccine().getId());
        response.setVaccineName(schedule.getVaccine().getName());
        response.setVaccineDescription(schedule.getVaccine().getDescription());
        response.setDueDate(schedule.getDueDate());
        response.setStatus(schedule.getStatus());
        response.setCreatedAt(schedule.getCreatedAt());
        response.setOverdue(schedule.getDueDate().isBefore(LocalDate.now()) && 
                           schedule.getStatus() == VaccinationSchedule.Status.PENDING);
        return response;
    }

    private VaccinationRecordResponse convertRecordToResponse(VaccinationRecord record) {
        VaccinationRecordResponse response = new VaccinationRecordResponse();
        response.setId(record.getId());
        response.setScheduleId(record.getSchedule().getId());
        response.setDoctorName(record.getDoctor().getFirstName() + " " + record.getDoctor().getLastName());
        response.setChildName(record.getSchedule().getChild().getFirstName() + " " + 
                             record.getSchedule().getChild().getLastName());
        response.setVaccineName(record.getSchedule().getVaccine().getName());
        response.setVaccinationDate(record.getVaccinationDate());
        response.setBatchNumber(record.getBatchNumber());
        response.setNotes(record.getNotes());
        response.setCreatedAt(record.getCreatedAt());
        return response;
    }
}
