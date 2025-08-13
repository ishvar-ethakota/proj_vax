package com.vaxtracker.service;

import com.vaxtracker.entity.Child;
import com.vaxtracker.entity.Vaccine;
import com.vaxtracker.entity.VaccinationSchedule;
import com.vaxtracker.repository.VaccinationScheduleRepository;
import com.vaxtracker.repository.VaccineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class VaccinationScheduleService {

    @Autowired
    private VaccinationScheduleRepository scheduleRepository;

    @Autowired
    private VaccineRepository vaccineRepository;

    public void generateScheduleForChild(Child child) {
        List<Vaccine> vaccines = vaccineRepository.findAllOrderByAge();
        
        for (Vaccine vaccine : vaccines) {
            LocalDate dueDate = child.getDateOfBirth().plusMonths(vaccine.getAgeInMonths());
            
            VaccinationSchedule schedule = new VaccinationSchedule();
            schedule.setChild(child);
            schedule.setVaccine(vaccine);
            schedule.setDueDate(dueDate);
            schedule.setStatus(VaccinationSchedule.Status.PENDING);
            
            scheduleRepository.save(schedule);
        }
    }
}
