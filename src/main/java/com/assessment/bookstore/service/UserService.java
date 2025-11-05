package com.assessment.bookstore.service;

import com.assessment.bookstore.model.request.LoginRequest;
import com.assessment.bookstore.model.request.RegisterRequest;
import com.assessment.bookstore.model.response.LoginResponse;
import com.assessment.bookstore.model.response.RegisterResponse;

public interface UserService {
    RegisterResponse registerUser(RegisterRequest request);

    LoginResponse loginUser(LoginRequest request);
}
