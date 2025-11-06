package com.assessment.bookstore.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class BookResponse {
    private String id;
    private String title;
    private String author;
    private BigDecimal price;
    private Integer stock;
    private Integer year;
    private String categoryId;
    private String categoryName;
    private String imageBase64;
}

