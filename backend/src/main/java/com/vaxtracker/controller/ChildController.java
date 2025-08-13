package com.vaxtracker.controller;

import com.vaxtracker.dto.ChildRequest;
import com.vaxtracker.dto.ChildResponse;
import com.vaxtracker.service.ChildService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/children")
@CrossOrigin(origins = "*")
public class ChildController {

    @Autowired
    private ChildService childService;

    @GetMapping
    public ResponseEntity<List<ChildResponse>> getChildren(Authentication authentication) {
        List<ChildResponse> children = childService.getChildrenByParent(authentication.getName());
        return ResponseEntity.ok(children);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChildResponse> getChild(@PathVariable Long id, Authentication authentication) {
        ChildResponse child = childService.getChildById(id, authentication.getName());
        return ResponseEntity.ok(child);
    }

    @PostMapping
    public ResponseEntity<ChildResponse> addChild(@Valid @RequestBody ChildRequest request, Authentication authentication) {
        ChildResponse child = childService.addChild(request, authentication.getName());
        return ResponseEntity.ok(child);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChildResponse> updateChild(@PathVariable Long id, @Valid @RequestBody ChildRequest request, Authentication authentication) {
        ChildResponse child = childService.updateChild(id, request, authentication.getName());
        return ResponseEntity.ok(child);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteChild(@PathVariable Long id, Authentication authentication) {
        childService.deleteChild(id, authentication.getName());
        return ResponseEntity.ok("Child deleted successfully");
    }

    @PostMapping("/{id}/photo")
    public ResponseEntity<String> uploadPhoto(@PathVariable Long id, @RequestParam("photo") MultipartFile photo, Authentication authentication) {
        String photoUrl = childService.uploadPhoto(id, photo, authentication.getName());
        return ResponseEntity.ok(photoUrl);
    }
}
