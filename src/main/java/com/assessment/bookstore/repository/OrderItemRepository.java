package com.assessment.bookstore.repository;

import com.assessment.bookstore.model.entity.Order;
import com.assessment.bookstore.model.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OrderItemRepository extends JpaRepository<OrderItem, UUID> {
    List<OrderItem> findByOrder(Order order);
}
