package com.assessment.bookstore.service;

import com.assessment.bookstore.model.request.CreateOrderRequest;
import com.assessment.bookstore.model.response.OrderResponse;
import com.assessment.bookstore.model.response.SearchResponse;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface OrderService {
    @Transactional
    OrderResponse createOrder(CreateOrderRequest request);

    @Transactional
    OrderResponse payOrder(UUID orderId);

    @Transactional(readOnly = true)
    OrderResponse getOrderById(UUID orderId);

    @Transactional(readOnly = true)
    SearchResponse<OrderResponse> getAllOrders(int pageNumber, int pageSize);
}
