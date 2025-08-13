package com.vaxtracker.repository;

import com.vaxtracker.entity.Child;
import com.vaxtracker.entity.DoctorPatient;
import com.vaxtracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorPatientRepository extends JpaRepository<DoctorPatient, Long> {
    List<DoctorPatient> findByDoctorAndIsActiveTrue(User doctor);
    
    List<DoctorPatient> findByChildAndIsActiveTrue(Child child);
    
    boolean existsByDoctorAndChildAndIsActiveTrue(User doctor, Child child);
    
    List<DoctorPatient> findByDoctorId(Long doctorId);
}
