package com.assessment.bookstore.service;

import com.assessment.bookstore.model.entity.User;

import java.util.Optional;

public interface RoleService {
    Optional<User.Role> getRole(String roleStr);
}
