package com.assessment.bookstore.repository;

import com.assessment.bookstore.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    boolean existsByRole(User.Role role);
    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
}
