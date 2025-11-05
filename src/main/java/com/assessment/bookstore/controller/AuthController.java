package com.assessment.bookstore.controller;

import com.assessment.bookstore.model.request.LoginRequest;
import com.assessment.bookstore.model.request.RegisterRequest;
import com.assessment.bookstore.model.response.ApiResponse;
import com.assessment.bookstore.model.response.LoginResponse;
import com.assessment.bookstore.model.response.RegisterResponse;
import com.assessment.bookstore.service.UserService;
import com.assessment.bookstore.util.CommonConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<RegisterResponse>> registerUser(@Validated @RequestBody RegisterRequest request) {

        RegisterResponse responseData = userService.registerUser(request);

        ApiResponse<RegisterResponse> response = new ApiResponse<>(
                CommonConstant.SUCCESS_CODE,
                "User registered successfully",
                responseData
        );

        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> loginUser(@Validated @RequestBody LoginRequest request) {

        LoginResponse responseData = userService.loginUser(request);

        ApiResponse<LoginResponse> response = new ApiResponse<>(
                CommonConstant.SUCCESS_CODE,
                "Login successful",
                responseData
        );
        return ResponseEntity.ok(response);
    }
}