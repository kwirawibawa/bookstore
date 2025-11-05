package com.assessment.bookstore.controller;

import com.assessment.bookstore.model.request.AdminCreateRequest;
import com.assessment.bookstore.model.response.ApiResponse;
import com.assessment.bookstore.model.response.RegisterResponse;
import com.assessment.bookstore.service.AdminService;
import com.assessment.bookstore.util.CommonConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/create-admin")
    public ResponseEntity<ApiResponse<RegisterResponse>> createFirstAdmin(@Validated @RequestBody AdminCreateRequest request) {
        return ResponseEntity.ok(new ApiResponse<>(CommonConstant.SUCCESS_CODE,
                "Admin created successfully", adminService.createFirstAdmin(request)));
    }
}
