package com.assessment.bookstore.service;

import com.assessment.bookstore.model.entity.User;
import com.assessment.bookstore.model.request.AdminCreateRequest;
import com.assessment.bookstore.model.response.RegisterResponse;
import com.assessment.bookstore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService{

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public RegisterResponse createFirstAdmin(AdminCreateRequest request) {
        if (userRepository.existsByRole(User.Role.ADMIN)) {
            throw new IllegalStateException("Admin already exists");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(User.Role.ADMIN);
        user.setCreatedAt(LocalDateTime.now());
        user.setCreatedBy(String.valueOf(User.Role.ADMIN));

        User savedUser = userRepository.save(user);

        RegisterResponse response = new RegisterResponse();
        response.setId(savedUser.getId().toString());
        response.setName(savedUser.getName());
        response.setEmail(savedUser.getEmail());
        response.setRole(savedUser.getRole().name());

        return response;
    }
}
