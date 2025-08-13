package com.vaxtracker.controller;

import com.vaxtracker.dto.VaccineResponse;
import com.vaxtracker.service.VaccineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vaccines")
@CrossOrigin(origins = "*")
public class VaccineController {

    @Autowired
    private VaccineService vaccineService;

    @GetMapping
    public ResponseEntity<List<VaccineResponse>> getAllVaccines() {
        List<VaccineResponse> vaccines = vaccineService.getAllVaccines();
        return ResponseEntity.ok(vaccines);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VaccineResponse> getVaccine(@PathVariable Long id) {
        VaccineResponse vaccine = vaccineService.getVaccineById(id);
        return ResponseEntity.ok(vaccine);
    }

    @GetMapping("/mandatory")
    public ResponseEntity<List<VaccineResponse>> getMandatoryVaccines() {
        List<VaccineResponse> vaccines = vaccineService.getMandatoryVaccines();
        return ResponseEntity.ok(vaccines);
    }

    @GetMapping("/by-age/{ageInMonths}")
    public ResponseEntity<List<VaccineResponse>> getVaccinesByAge(@PathVariable Integer ageInMonths) {
        List<VaccineResponse> vaccines = vaccineService.getVaccinesByAge(ageInMonths);
        return ResponseEntity.ok(vaccines);
    }
}
