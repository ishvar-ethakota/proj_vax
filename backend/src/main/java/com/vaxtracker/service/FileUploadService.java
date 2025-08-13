package com.vaxtracker.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileUploadService {

    @Value("${file.upload.dir}")
    private String uploadDir;

    public String uploadFile(MultipartFile file, String category) {
        try {
            // Create directory if it doesn't exist
            Path uploadPath = Paths.get(uploadDir, category);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Generate unique filename
            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String filename = UUID.randomUUID().toString() + extension;

            // Save file
            Path filePath = uploadPath.resolve(filename);
            Files.copy(file.getInputStream(), filePath);

            // Return relative URL
            return "/" + category + "/" + filename;

        } catch (IOException e) {
            throw new RuntimeException("Failed to upload file", e);
        }
    }
}
