package com.assessment.bookstore.service;

import com.assessment.bookstore.model.request.LoginRequest;
import com.assessment.bookstore.model.request.RegisterRequest;
import com.assessment.bookstore.model.response.LoginResponse;
import com.assessment.bookstore.model.response.RegisterResponse;
import org.springframework.transaction.annotation.Transactional;

public interface UserService {
    @Transactional
    RegisterResponse registerUser(RegisterRequest request);

    LoginResponse loginUser(LoginRequest request);
}
