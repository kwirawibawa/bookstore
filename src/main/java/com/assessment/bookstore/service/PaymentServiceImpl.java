package com.assessment.bookstore.service;

import com.assessment.bookstore.model.entity.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Transactional
    @Override
    public boolean simulatePayment(Order order) {
        // Asumsi pembayaran berhasil
        return true;
    }
}
