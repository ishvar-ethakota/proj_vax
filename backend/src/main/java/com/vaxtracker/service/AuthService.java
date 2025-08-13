package com.vaxtracker.service;

import com.vaxtracker.dto.LoginRequest;
import com.vaxtracker.dto.LoginResponse;
import com.vaxtracker.dto.RegisterRequest;
import com.vaxtracker.dto.UserResponse;
import com.vaxtracker.entity.User;
import com.vaxtracker.repository.UserRepository;
import com.vaxtracker.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    public UserResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setRole(request.getRole());
        user.setPreferredLanguage(request.getPreferredLanguage());

        User savedUser = userRepository.save(user);
        return convertToUserResponse(savedUser);
    }

    public LoginResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        String token = tokenProvider.generateToken(authentication);
        User user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new RuntimeException("User not found"));

        return new LoginResponse(token, convertToUserResponse(user));
    }

    public LoginResponse refreshToken(String token) {
        String email = tokenProvider.getUsernameFromToken(token.substring(7));
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found"));

        String newToken = tokenProvider.generateTokenFromUsername(email);
        return new LoginResponse(newToken, convertToUserResponse(user));
    }

    public void logout(String token) {
        // In a real application, you might want to blacklist the token
        // For now, we'll just acknowledge the logout
    }

    private UserResponse convertToUserResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setEmail(user.getEmail());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setPhoneNumber(user.getPhoneNumber());
        response.setRole(user.getRole());
        response.setPreferredLanguage(user.getPreferredLanguage());
        response.setCreatedAt(user.getCreatedAt());
        return response;
    }
}
