package com.assessment.bookstore.controller;

import com.assessment.bookstore.model.dto.BestSellerBookDTO;
import com.assessment.bookstore.model.dto.PriceReportDTO;
import com.assessment.bookstore.model.dto.SalesReportDTO;
import com.assessment.bookstore.model.response.ApiResponse;
import com.assessment.bookstore.service.ReportService;
import com.assessment.bookstore.util.CommonConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/sales")
    public ApiResponse<SalesReportDTO> getSalesReport() {
        return new ApiResponse<>(CommonConstant.SUCCESS_CODE, "Sales report retrieved", reportService.getSalesReport());
    }

    @GetMapping("/bestseller")
    public ApiResponse<List<BestSellerBookDTO>> getBestSeller() {
        return new ApiResponse<>(CommonConstant.SUCCESS_CODE, "Best seller books retrieved", reportService.getBestSellerBooks(3));
    }

    @GetMapping("/prices")
    public ApiResponse<PriceReportDTO> getPriceReport() {
        return new ApiResponse<>(CommonConstant.SUCCESS_CODE, "Book price report retrieved", reportService.getPriceReport());
    }
}