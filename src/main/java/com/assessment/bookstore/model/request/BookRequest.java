package com.assessment.bookstore.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookRequest {
    @NotBlank
    private String title;

    private String author;

    @NotNull
    private BigDecimal price;

    @NotNull
    private Integer stock;

    private Integer publishYear;

    @NotNull
    private UUID categoryId;

    private String imageBase64;
}
