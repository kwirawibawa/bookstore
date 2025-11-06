package com.assessment.bookstore.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PriceReportDTO {
    private BigDecimal maxPrice;
    private BigDecimal minPrice;
    private BigDecimal avgPrice;
}
