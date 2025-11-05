package com.assessment.bookstore.service;

import com.assessment.bookstore.model.entity.User;
import com.assessment.bookstore.model.request.AdminCreateRequest;
import com.assessment.bookstore.model.response.RegisterResponse;

public interface AdminService {
    RegisterResponse createFirstAdmin(AdminCreateRequest request);
}
