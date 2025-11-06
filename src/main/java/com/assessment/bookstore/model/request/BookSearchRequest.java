package com.assessment.bookstore.model.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookSearchRequest {
    private String search;
    private UUID categoryId;

    @NotNull(message = "Page number must not be null")
    @Min(value = 1, message = "Page number must be greater than 0")
    private Integer pageNumber;

    @NotNull(message = "Page size must not be null")
    @Min(value = 1, message = "Page size must be greater than 0")
    private Integer pageSize;
}
