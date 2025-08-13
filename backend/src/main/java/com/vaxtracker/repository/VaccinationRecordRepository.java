package com.vaxtracker.repository;

import com.vaxtracker.entity.User;
import com.vaxtracker.entity.VaccinationRecord;
import com.vaxtracker.entity.VaccinationSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VaccinationRecordRepository extends JpaRepository<VaccinationRecord, Long> {
    List<VaccinationRecord> findBySchedule(VaccinationSchedule schedule);
    
    List<VaccinationRecord> findByDoctor(User doctor);
    
    List<VaccinationRecord> findByDoctorOrderByCreatedAtDesc(User doctor);
    
    List<VaccinationRecord> findByScheduleChildId(Long childId);
}
