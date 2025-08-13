package com.vaxtracker.service;

import com.vaxtracker.entity.Child;
import com.vaxtracker.entity.Reminder;
import com.vaxtracker.entity.User;
import com.vaxtracker.entity.VaccinationSchedule;
import com.vaxtracker.repository.ChildRepository;
import com.vaxtracker.repository.ReminderRepository;
import com.vaxtracker.repository.VaccinationScheduleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ReminderService {
    
    private static final Logger logger = LoggerFactory.getLogger(ReminderService.class);
    
    @Autowired
    private VaccinationScheduleRepository vaccinationScheduleRepository;
    
    @Autowired
    private ReminderRepository reminderRepository;
    
    @Autowired
    private ChildRepository childRepository;
    
    @Autowired
    private SmsService smsService;
    
    // Run every day at 9:00 AM
    @Scheduled(cron = "0 0 9 * * ?")
    public void sendDailyReminders() {
        logger.info("Starting daily reminder check...");
        
        // Get vaccinations due in the next 3 days
        LocalDate today = LocalDate.now();
        LocalDate reminderDate = today.plusDays(3);
        
        List<VaccinationSchedule> upcomingVaccinations = 
            vaccinationScheduleRepository.findByDueDateBetweenAndStatus(
                today, reminderDate, "PENDING"
            );
        
        logger.info("Found {} upcoming vaccinations", upcomingVaccinations.size());
        
        for (VaccinationSchedule vaccination : upcomingVaccinations) {
            sendVaccinationReminder(vaccination);
        }
        
        logger.info("Daily reminder check completed");
    }
    
    // Run every Monday at 10:00 AM for weekly summary
    @Scheduled(cron = "0 0 10 * * MON")
    public void sendWeeklyReminders() {
        logger.info("Starting weekly reminder check...");
        
        LocalDate today = LocalDate.now();
        LocalDate weekEnd = today.plusDays(7);
        
        List<VaccinationSchedule> weeklyVaccinations = 
            vaccinationScheduleRepository.findByDueDateBetweenAndStatus(
                today, weekEnd, "PENDING"
            );
        
        // Group by child and send summary
        weeklyVaccinations.stream()
            .collect(java.util.stream.Collectors.groupingBy(VaccinationSchedule::getChild))
            .forEach(this::sendWeeklySummary);
        
        logger.info("Weekly reminder check completed");
    }
    
    private void sendVaccinationReminder(VaccinationSchedule vaccination) {
        try {
            Child child = vaccination.getChild();
            User parent = child.getParent();
            
            // Check if reminder already sent for this vaccination
            boolean reminderExists = reminderRepository.existsByVaccinationScheduleAndReminderDate(
                vaccination, LocalDate.now()
            );
            
            if (reminderExists) {
                logger.debug("Reminder already sent for vaccination ID: {}", vaccination.getId());
                return;
            }
            
            // Get parent's preferred language
            String language = parent.getPreferredLanguage() != null ? 
                parent.getPreferredLanguage() : "english";
            
            // Format due date
            String formattedDate = vaccination.getDueDate().format(
                DateTimeFormatter.ofPattern("dd/MM/yyyy")
            );
            
            // Send SMS reminder
            boolean smsSent = smsService.sendVaccineReminder(
                parent.getPhoneNumber(),
                child.getName(),
                vaccination.getVaccine().getName(),
                formattedDate,
                language
            );
            
            // Create reminder record
            Reminder reminder = new Reminder();
            reminder.setVaccinationSchedule(vaccination);
            reminder.setReminderDate(LocalDate.now());
            reminder.setReminderType("SMS");
            reminder.setStatus(smsSent ? "SENT" : "FAILED");
            reminder.setMessage(buildReminderMessage(child.getName(), 
                vaccination.getVaccine().getName(), formattedDate, language));
            
            reminderRepository.save(reminder);
            
            logger.info("Reminder {} for child: {}, vaccine: {}", 
                smsSent ? "sent" : "failed", child.getName(), vaccination.getVaccine().getName());
            
        } catch (Exception e) {
            logger.error("Error sending reminder for vaccination ID {}: {}", 
                vaccination.getId(), e.getMessage());
        }
    }
    
    private void sendWeeklySummary(Child child, List<VaccinationSchedule> vaccinations) {
        try {
            User parent = child.getParent();
            String language = parent.getPreferredLanguage() != null ? 
                parent.getPreferredLanguage() : "english";
            
            StringBuilder message = new StringBuilder();
            
            switch (language.toLowerCase()) {
                case "hindi":
                    message.append("🗓️ साप्ताहिक टीकाकरण सारांश - ").append(child.getName()).append(":\n");
                    break;
                case "telugu":
                    message.append("🗓️ వారపు వ్యాక్సినేషన్ సారాంశం - ").append(child.getName()).append(":\n");
                    break;
                default:
                    message.append("🗓️ Weekly Vaccination Summary - ").append(child.getName()).append(":\n");
            }
            
            for (VaccinationSchedule vaccination : vaccinations) {
                String dueDate = vaccination.getDueDate().format(DateTimeFormatter.ofPattern("dd/MM"));
                message.append("• ").append(vaccination.getVaccine().getName())
                       .append(" - ").append(dueDate).append("\n");
            }
            
            message.append("\n- VaxTracker");
            
            smsService.sendSms(parent.getPhoneNumber(), message.toString());
            
        } catch (Exception e) {
            logger.error("Error sending weekly summary for child {}: {}", 
                child.getName(), e.getMessage());
        }
    }
    
    private String buildReminderMessage(String childName, String vaccineName, String dueDate, String language) {
        switch (language.toLowerCase()) {
            case "hindi":
                return String.format("🩺 टीकाकरण अनुस्मारक: %s का %s टीकाकरण %s को देय है। कृपया अपने डॉक्टर से अपॉइंटमेंट लें। - VaxTracker", 
                    childName, vaccineName, dueDate);
            case "telugu":
                return String.format("🩺 వ్యాక్సిన్ రిమైండర్: %s యొక్క %s వ్యాక్సినేషన్ %sన చేయించాలి. దయచేసి మీ వైద్యుడితో అపాయింట్మెంట్ తీసుకోండి। - VaxTracker", 
                    childName, vaccineName, dueDate);
            default:
                return String.format("🩺 VACCINE REMINDER: %s's %s vaccination is due on %s. Please schedule an appointment with your doctor. - VaxTracker", 
                    childName, vaccineName, dueDate);
        }
    }
}
