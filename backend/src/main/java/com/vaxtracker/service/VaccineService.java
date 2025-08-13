package com.vaxtracker.service;

import com.vaxtracker.dto.VaccineResponse;
import com.vaxtracker.entity.Vaccine;
import com.vaxtracker.repository.VaccineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VaccineService {

    @Autowired
    private VaccineRepository vaccineRepository;

    public List<VaccineResponse> getAllVaccines() {
        List<Vaccine> vaccines = vaccineRepository.findAllOrderByAge();
        return vaccines.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public VaccineResponse getVaccineById(Long id) {
        Vaccine vaccine = vaccineRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vaccine not found"));
        return convertToResponse(vaccine);
    }

    public List<VaccineResponse> getMandatoryVaccines() {
        List<Vaccine> vaccines = vaccineRepository.findByIsMandatoryTrue();
        return vaccines.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public List<VaccineResponse> getVaccinesByAge(Integer ageInMonths) {
        List<Vaccine> vaccines = vaccineRepository.findVaccinesDueByAge(ageInMonths);
        return vaccines.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    private VaccineResponse convertToResponse(Vaccine vaccine) {
        VaccineResponse response = new VaccineResponse();
        response.setId(vaccine.getId());
        response.setName(vaccine.getName());
        response.setDescription(vaccine.getDescription());
        response.setImportance(vaccine.getImportance());
        response.setSideEffects(vaccine.getSideEffects());
        response.setAgeInMonths(vaccine.getAgeInMonths());
        response.setIsMandatory(vaccine.getIsMandatory());
        response.setCreatedAt(vaccine.getCreatedAt());
        return response;
    }
}
