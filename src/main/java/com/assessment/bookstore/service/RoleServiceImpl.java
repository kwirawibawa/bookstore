package com.assessment.bookstore.service;

import com.assessment.bookstore.model.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    @Override
    public Optional<User.Role> getRole(String roleStr) {
        try {
            return Optional.of(User.Role.valueOf(roleStr.toUpperCase()));
        } catch (IllegalArgumentException ex) {
            return Optional.empty();
        }
    }
}
