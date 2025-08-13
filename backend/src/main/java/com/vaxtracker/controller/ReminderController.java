package com.vaxtracker.controller;

import com.vaxtracker.dto.ApiResponse;
import com.vaxtracker.entity.Reminder;
import com.vaxtracker.repository.ReminderRepository;
import com.vaxtracker.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/reminders")
@CrossOrigin(origins = "*")
public class ReminderController {
    
    @Autowired
    private ReminderRepository reminderRepository;
    
    @Autowired
    private SmsService smsService;
    
    @GetMapping("/user")
    @PreAuthorize("hasRole('PARENT')")
    public ResponseEntity<ApiResponse<List<Reminder>>> getUserReminders(Principal principal) {
        try {
            String email = principal.getName();
            List<Reminder> reminders = reminderRepository.findByUserEmailOrderByReminderDateDesc(email);
            
            return ResponseEntity.ok(new ApiResponse<>(true, "Reminders retrieved successfully", reminders));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(new ApiResponse<>(false, "Error retrieving reminders: " + e.getMessage(), null));
        }
    }
    
    @PostMapping("/test-sms")
    @PreAuthorize("hasRole('PARENT')")
    public ResponseEntity<ApiResponse<String>> testSms(@RequestBody Map<String, String> request, Principal principal) {
        try {
            String phoneNumber = request.get("phoneNumber");
            String message = request.get("message");
            
            if (phoneNumber == null || message == null) {
                return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "Phone number and message are required", null));
            }
            
            boolean sent = smsService.sendSms(phoneNumber, message);
            
            if (sent) {
                return ResponseEntity.ok(new ApiResponse<>(true, "Test SMS sent successfully", "SMS delivered"));
            } else {
                return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "Failed to send test SMS", null));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(new ApiResponse<>(false, "Error sending test SMS: " + e.getMessage(), null));
        }
    }
    
    @PostMapping("/send-manual")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<ApiResponse<String>> sendManualReminder(@RequestBody Map<String, Object> request) {
        try {
            String phoneNumber = (String) request.get("phoneNumber");
            String childName = (String) request.get("childName");
            String vaccineName = (String) request.get("vaccineName");
            String dueDate = (String) request.get("dueDate");
            String language = (String) request.get("language");
            
            boolean sent = smsService.sendVaccineReminder(phoneNumber, childName, vaccineName, dueDate, language);
            
            if (sent) {
                return ResponseEntity.ok(new ApiResponse<>(true, "Manual reminder sent successfully", "Reminder delivered"));
            } else {
                return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "Failed to send manual reminder", null));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(new ApiResponse<>(false, "Error sending manual reminder: " + e.getMessage(), null));
        }
    }
}
