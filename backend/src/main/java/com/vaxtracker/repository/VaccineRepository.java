package com.vaxtracker.repository;

import com.vaxtracker.entity.Vaccine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VaccineRepository extends JpaRepository<Vaccine, Long> {
    List<Vaccine> findByIsMandatoryTrue();
    
    List<Vaccine> findByAgeInMonthsLessThanEqual(Integer ageInMonths);
    
    @Query("SELECT v FROM Vaccine v WHERE v.ageInMonths <= :ageInMonths ORDER BY v.ageInMonths")
    List<Vaccine> findVaccinesDueByAge(@Param("ageInMonths") Integer ageInMonths);
    
    @Query("SELECT v FROM Vaccine v ORDER BY v.ageInMonths")
    List<Vaccine> findAllOrderByAge();
}
