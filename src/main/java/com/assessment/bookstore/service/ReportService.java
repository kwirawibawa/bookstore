package com.assessment.bookstore.service;

import com.assessment.bookstore.model.dto.BestSellerBookDTO;
import com.assessment.bookstore.model.dto.PriceReportDTO;
import com.assessment.bookstore.model.dto.SalesReportDTO;

import java.util.List;

public interface ReportService {
    SalesReportDTO getSalesReport();

    List<BestSellerBookDTO> getBestSellerBooks(int topN);

    PriceReportDTO getPriceReport();
}
