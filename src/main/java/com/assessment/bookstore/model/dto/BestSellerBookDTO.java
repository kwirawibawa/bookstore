package com.assessment.bookstore.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BestSellerBookDTO {
    private String title;
    private Long totalBooksSold;
}
