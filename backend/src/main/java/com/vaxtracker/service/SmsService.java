package com.vaxtracker.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;

@Service
public class SmsService {
    
    private static final Logger logger = LoggerFactory.getLogger(SmsService.class);
    
    @Value("${twilio.account.sid}")
    private String accountSid;
    
    @Value("${twilio.auth.token}")
    private String authToken;
    
    @Value("${twilio.phone.number}")
    private String fromPhoneNumber;
    
    @PostConstruct
    public void init() {
        Twilio.init(accountSid, authToken);
        logger.info("Twilio SMS service initialized");
    }
    
    public boolean sendSms(String toPhoneNumber, String messageBody) {
        try {
            // Format phone number to include country code if not present
            String formattedNumber = formatPhoneNumber(toPhoneNumber);
            
            Message message = Message.creator(
                new PhoneNumber(formattedNumber),
                new PhoneNumber(fromPhoneNumber),
                messageBody
            ).create();
            
            logger.info("SMS sent successfully. SID: {}", message.getSid());
            return true;
            
        } catch (Exception e) {
            logger.error("Failed to send SMS to {}: {}", toPhoneNumber, e.getMessage());
            return false;
        }
    }
    
    public boolean sendVaccineReminder(String toPhoneNumber, String childName, 
                                     String vaccineName, String dueDate, String language) {
        String message = buildReminderMessage(childName, vaccineName, dueDate, language);
        return sendSms(toPhoneNumber, message);
    }
    
    private String buildReminderMessage(String childName, String vaccineName, String dueDate, String language) {
        Map<String, String> templates = getReminderTemplates();
        String template = templates.getOrDefault(language.toLowerCase(), templates.get("english"));
        
        return template
            .replace("{childName}", childName)
            .replace("{vaccineName}", vaccineName)
            .replace("{dueDate}", dueDate);
    }
    
    private Map<String, String> getReminderTemplates() {
        return Map.of(
            "english", "🩺 VACCINE REMINDER: {childName}'s {vaccineName} vaccination is due on {dueDate}. Please schedule an appointment with your doctor. - VaxTracker",
            "hindi", "🩺 टीकाकरण अनुस्मारक: {childName} का {vaccineName} टीकाकरण {dueDate} को देय है। कृपया अपने डॉक्टर से अपॉइंटमेंट लें। - VaxTracker",
            "telugu", "🩺 వ్యాక్సిన్ రిమైండర్: {childName} యొక్క {vaccineName} వ్యాక్సినేషన్ {dueDate}న చేయించాలి. దయచేసి మీ వైద్యుడితో అపాయింట్మెంట్ తీసుకోండి. - VaxTracker"
        );
    }
    
    private String formatPhoneNumber(String phoneNumber) {
        // Remove all non-digit characters
        String cleaned = phoneNumber.replaceAll("[^\\d]", "");
        
        // Add country code if not present (assuming India +91)
        if (cleaned.length() == 10) {
            cleaned = "+91" + cleaned;
        } else if (cleaned.length() == 12 && cleaned.startsWith("91")) {
            cleaned = "+" + cleaned;
        } else if (!cleaned.startsWith("+")) {
            cleaned = "+" + cleaned;
        }
        
        return cleaned;
    }
}
