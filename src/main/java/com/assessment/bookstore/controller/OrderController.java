package com.assessment.bookstore.controller;

import com.assessment.bookstore.model.request.CreateOrderRequest;
import com.assessment.bookstore.model.response.ApiResponse;
import com.assessment.bookstore.model.response.OrderResponse;
import com.assessment.bookstore.model.response.SearchResponse;
import com.assessment.bookstore.service.OrderService;
import com.assessment.bookstore.util.CommonConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ApiResponse<OrderResponse> createOrder(@Validated @RequestBody CreateOrderRequest request) {
        return new ApiResponse<>(CommonConstant.SUCCESS_CODE, "Order created successfully", orderService.createOrder(request));
    }

    @PostMapping("/{id}/pay")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ApiResponse<OrderResponse> payOrder(@PathVariable UUID id) {
        return new ApiResponse<>(CommonConstant.SUCCESS_CODE, "Payment successful", orderService.payOrder(id));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ApiResponse<OrderResponse> getOrder(@PathVariable UUID id) {
        return new ApiResponse<>(CommonConstant.SUCCESS_CODE, "Order retrieved successfully", orderService.getOrderById(id));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public SearchResponse<OrderResponse> getOrders( @RequestParam(defaultValue = "1") int pageNumber,
                                                    @RequestParam(defaultValue = "10") int pageSize) {
        return orderService.getAllOrders(pageNumber, pageSize);
    }
}
