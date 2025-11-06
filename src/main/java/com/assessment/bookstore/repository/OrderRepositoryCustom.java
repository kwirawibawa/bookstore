package com.assessment.bookstore.repository;

import com.assessment.bookstore.model.entity.Order;

import java.util.List;
import java.util.UUID;

public interface OrderRepositoryCustom {
    List<Order> findOrders(UUID userId, boolean isAdmin, int pageNumber, int pageSize);
    long countOrders(UUID userId, boolean isAdmin);
}
