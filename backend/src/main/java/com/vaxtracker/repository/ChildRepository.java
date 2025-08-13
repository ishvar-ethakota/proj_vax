package com.vaxtracker.repository;

import com.vaxtracker.entity.Child;
import com.vaxtracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ChildRepository extends JpaRepository<Child, Long> {
    List<Child> findByParent(User parent);
    
    List<Child> findByParentId(Long parentId);
    
    @Query("SELECT c FROM Child c WHERE c.dateOfBirth BETWEEN :startDate AND :endDate")
    List<Child> findByDateOfBirthBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    
    @Query("SELECT c FROM Child c WHERE c.parent.id = :parentId ORDER BY c.dateOfBirth DESC")
    List<Child> findByParentIdOrderByDateOfBirthDesc(@Param("parentId") Long parentId);
}
