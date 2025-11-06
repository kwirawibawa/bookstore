package com.assessment.bookstore.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderResponse {
    private UUID id;
    private UUID userId;
    private BigDecimal totalPrice;
    private String status;
    private List<OrderItemResponse> items;
    private LocalDateTime createdAt;
    private LocalDateTime paidAt;
}
