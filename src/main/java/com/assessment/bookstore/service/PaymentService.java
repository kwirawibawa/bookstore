package com.assessment.bookstore.service;

import com.assessment.bookstore.model.entity.Order;
import org.springframework.transaction.annotation.Transactional;

public interface PaymentService {
    @Transactional
    boolean simulatePayment(Order order);
}
