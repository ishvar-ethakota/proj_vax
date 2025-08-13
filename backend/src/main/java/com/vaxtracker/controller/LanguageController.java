package com.vaxtracker.controller;

import com.vaxtracker.dto.ApiResponse;
import com.vaxtracker.entity.User;
import com.vaxtracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("/language")
@CrossOrigin(origins = "*")
public class LanguageController {
    
    @Autowired
    private UserRepository userRepository;
    
    @GetMapping("/supported")
    public ResponseEntity<ApiResponse<Map<String, String>>> getSupportedLanguages() {
        Map<String, String> languages = Map.of(
            "en", "English",
            "hi", "हिंदी (Hindi)",
            "te", "తెలుగు (Telugu)"
        );
        
        return ResponseEntity.ok(new ApiResponse<>(true, "Supported languages retrieved", languages));
    }
    
    @PostMapping("/preference")
    @PreAuthorize("hasRole('PARENT') or hasRole('DOCTOR')")
    public ResponseEntity<ApiResponse<String>> updateLanguagePreference(
            @RequestBody Map<String, String> request, Principal principal) {
        try {
            String email = principal.getName();
            String language = request.get("language");
            
            if (language == null || (!language.equals("en") && !language.equals("hi") && !language.equals("te"))) {
                return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "Invalid language. Supported: en, hi, te", null));
            }
            
            User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
            
            user.setPreferredLanguage(language);
            userRepository.save(user);
            
            return ResponseEntity.ok(new ApiResponse<>(true, "Language preference updated", language));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(new ApiResponse<>(false, "Error updating language preference: " + e.getMessage(), null));
        }
    }
    
    @GetMapping("/preference")
    @PreAuthorize("hasRole('PARENT') or hasRole('DOCTOR')")
    public ResponseEntity<ApiResponse<String>> getLanguagePreference(Principal principal) {
        try {
            String email = principal.getName();
            User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
            
            String language = user.getPreferredLanguage() != null ? user.getPreferredLanguage() : "en";
            
            return ResponseEntity.ok(new ApiResponse<>(true, "Language preference retrieved", language));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(new ApiResponse<>(false, "Error retrieving language preference: " + e.getMessage(), null));
        }
    }
}
