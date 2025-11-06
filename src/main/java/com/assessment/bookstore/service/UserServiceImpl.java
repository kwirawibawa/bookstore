package com.assessment.bookstore.service;

import com.assessment.bookstore.model.entity.User;
import com.assessment.bookstore.model.request.LoginRequest;
import com.assessment.bookstore.model.request.RegisterRequest;
import com.assessment.bookstore.model.response.LoginResponse;
import com.assessment.bookstore.model.response.RegisterResponse;
import com.assessment.bookstore.repository.UserRepository;
import com.assessment.bookstore.security.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    @Transactional
    public RegisterResponse registerUser(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalStateException("Email already registered");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(User.Role.USER);
        user.setCreatedAt(LocalDateTime.now());
        user.setCreatedBy(String.valueOf(User.Role.USER));

        User savedUser = userRepository.save(user);

        RegisterResponse response = new RegisterResponse();
        response.setId(savedUser.getId().toString());
        response.setName(savedUser.getName());
        response.setEmail(savedUser.getEmail());
        response.setRole(savedUser.getRole().name());

        return response;
    }

    @Override
    public LoginResponse loginUser(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid credentials");
        }

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());
        Claims claims = jwtUtil.getClaims(token);

        return new LoginResponse(
                token,
                "Bearer",
                user.getId().toString(),
                user.getName(),
                user.getEmail(),
                user.getRole().name(),
                claims.getIssuedAt(),
                claims.getExpiration()
        );
    }
}
