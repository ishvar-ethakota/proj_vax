package com.vaxtracker.service;

import com.vaxtracker.dto.ChildRequest;
import com.vaxtracker.dto.ChildResponse;
import com.vaxtracker.entity.Child;
import com.vaxtracker.entity.User;
import com.vaxtracker.repository.ChildRepository;
import com.vaxtracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChildService {

    @Autowired
    private ChildRepository childRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FileUploadService fileUploadService;

    @Autowired
    private VaccinationScheduleService vaccinationScheduleService;

    public List<ChildResponse> getChildrenByParent(String parentEmail) {
        User parent = userRepository.findByEmail(parentEmail)
                .orElseThrow(() -> new RuntimeException("Parent not found"));

        List<Child> children = childRepository.findByParent(parent);
        return children.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public ChildResponse getChildById(Long childId, String parentEmail) {
        User parent = userRepository.findByEmail(parentEmail)
                .orElseThrow(() -> new RuntimeException("Parent not found"));

        Child child = childRepository.findById(childId)
                .orElseThrow(() -> new RuntimeException("Child not found"));

        if (!child.getParent().getId().equals(parent.getId())) {
            throw new RuntimeException("Access denied");
        }

        return convertToResponse(child);
    }

    public ChildResponse addChild(ChildRequest request, String parentEmail) {
        User parent = userRepository.findByEmail(parentEmail)
                .orElseThrow(() -> new RuntimeException("Parent not found"));

        Child child = new Child();
        child.setParent(parent);
        child.setFirstName(request.getFirstName());
        child.setLastName(request.getLastName());
        child.setDateOfBirth(request.getDateOfBirth());
        child.setGender(request.getGender());
        child.setBloodGroup(request.getBloodGroup());

        Child savedChild = childRepository.save(child);

        // Generate vaccination schedule for the child
        vaccinationScheduleService.generateScheduleForChild(savedChild);

        return convertToResponse(savedChild);
    }

    public ChildResponse updateChild(Long childId, ChildRequest request, String parentEmail) {
        User parent = userRepository.findByEmail(parentEmail)
                .orElseThrow(() -> new RuntimeException("Parent not found"));

        Child child = childRepository.findById(childId)
                .orElseThrow(() -> new RuntimeException("Child not found"));

        if (!child.getParent().getId().equals(parent.getId())) {
            throw new RuntimeException("Access denied");
        }

        child.setFirstName(request.getFirstName());
        child.setLastName(request.getLastName());
        child.setDateOfBirth(request.getDateOfBirth());
        child.setGender(request.getGender());
        child.setBloodGroup(request.getBloodGroup());

        Child savedChild = childRepository.save(child);
        return convertToResponse(savedChild);
    }

    public void deleteChild(Long childId, String parentEmail) {
        User parent = userRepository.findByEmail(parentEmail)
                .orElseThrow(() -> new RuntimeException("Parent not found"));

        Child child = childRepository.findById(childId)
                .orElseThrow(() -> new RuntimeException("Child not found"));

        if (!child.getParent().getId().equals(parent.getId())) {
            throw new RuntimeException("Access denied");
        }

        childRepository.delete(child);
    }

    public String uploadPhoto(Long childId, MultipartFile photo, String parentEmail) {
        User parent = userRepository.findByEmail(parentEmail)
                .orElseThrow(() -> new RuntimeException("Parent not found"));

        Child child = childRepository.findById(childId)
                .orElseThrow(() -> new RuntimeException("Child not found"));

        if (!child.getParent().getId().equals(parent.getId())) {
            throw new RuntimeException("Access denied");
        }

        String photoUrl = fileUploadService.uploadFile(photo, "children");
        child.setPhotoUrl(photoUrl);
        childRepository.save(child);

        return photoUrl;
    }

    private ChildResponse convertToResponse(Child child) {
        ChildResponse response = new ChildResponse();
        response.setId(child.getId());
        response.setFirstName(child.getFirstName());
        response.setLastName(child.getLastName());
        response.setDateOfBirth(child.getDateOfBirth());
        response.setGender(child.getGender());
        response.setBloodGroup(child.getBloodGroup());
        response.setPhotoUrl(child.getPhotoUrl());
        response.setCreatedAt(child.getCreatedAt());
        response.setAgeInMonths(calculateAgeInMonths(child.getDateOfBirth()));
        return response;
    }

    private Integer calculateAgeInMonths(LocalDate dateOfBirth) {
        Period period = Period.between(dateOfBirth, LocalDate.now());
        return period.getYears() * 12 + period.getMonths();
    }
}
