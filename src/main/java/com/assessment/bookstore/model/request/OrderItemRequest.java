package com.assessment.bookstore.model.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemRequest {
    @NotNull(message = "Book ID cannot be null")
    private UUID bookId;

    @Min(value = 1, message = "Quantity must be at least 1")
    private int quantity;
}
