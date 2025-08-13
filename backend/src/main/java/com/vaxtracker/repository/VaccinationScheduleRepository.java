package com.vaxtracker.repository;

import com.vaxtracker.entity.Child;
import com.vaxtracker.entity.VaccinationSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface VaccinationScheduleRepository extends JpaRepository<VaccinationSchedule, Long> {
    List<VaccinationSchedule> findByChild(Child child);
    
    List<VaccinationSchedule> findByChildId(Long childId);
    
    List<VaccinationSchedule> findByStatus(VaccinationSchedule.Status status);
    
    @Query("SELECT vs FROM VaccinationSchedule vs WHERE vs.dueDate BETWEEN :startDate AND :endDate")
    List<VaccinationSchedule> findByDueDateBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    
    @Query("SELECT vs FROM VaccinationSchedule vs WHERE vs.child.parent.id = :parentId AND vs.status = :status ORDER BY vs.dueDate")
    List<VaccinationSchedule> findByParentIdAndStatus(@Param("parentId") Long parentId, @Param("status") VaccinationSchedule.Status status);
    
    @Query("SELECT vs FROM VaccinationSchedule vs WHERE vs.dueDate <= :date AND vs.status = 'PENDING'")
    List<VaccinationSchedule> findOverdueSchedules(@Param("date") LocalDate date);
    
    @Query("SELECT vs FROM VaccinationSchedule vs WHERE vs.child.parent.id = :parentId ORDER BY vs.dueDate")
    List<VaccinationSchedule> findByParentIdOrderByDueDate(@Param("parentId") Long parentId);
    
    @Query("SELECT vs FROM VaccinationSchedule vs WHERE vs.dueDate BETWEEN :startDate AND :endDate AND vs.status = :status")
    List<VaccinationSchedule> findByDueDateBetweenAndStatus(@Param("startDate") LocalDate startDate, 
                                                           @Param("endDate") LocalDate endDate, 
                                                           @Param("status") String status);
}
