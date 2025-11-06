package com.assessment.bookstore.service;

import com.assessment.bookstore.model.dto.BestSellerBookDTO;
import com.assessment.bookstore.model.dto.PriceReportDTO;
import com.assessment.bookstore.model.dto.SalesReportDTO;
import com.assessment.bookstore.repository.ReportRepositoryCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final ReportRepositoryCustom reportRepository;

    @Override
    public SalesReportDTO getSalesReport() {
        Object[] result = reportRepository.getSalesReport();
        BigDecimal totalRevenue = (BigDecimal) result[0];
        Number totalBooksNumber = (Number) result[1];
        long totalBooksSold = totalBooksNumber != null ? totalBooksNumber.longValue() : 0L;

        return new SalesReportDTO(totalRevenue, totalBooksSold);
    }

    @Override
    public List<BestSellerBookDTO> getBestSellerBooks(int topN) {
        List<Object[]> results = reportRepository.getBestSellerBooks(topN);
        return results.stream()
                .map(r -> new BestSellerBookDTO(
                        (String) r[0],
                        ((Number) r[1]).longValue()
                ))
                .toList();
    }

    @Override
    public PriceReportDTO getPriceReport() {
        Object[] result = reportRepository.getPriceReport();

        BigDecimal maxPrice = (BigDecimal) result[0];
        BigDecimal minPrice = (BigDecimal) result[1];
        Number avgNumber = (Number) result[2];
        BigDecimal avgPrice = avgNumber != null ? BigDecimal.valueOf(avgNumber.doubleValue()) : BigDecimal.ZERO;

        return new PriceReportDTO(maxPrice, minPrice, avgPrice);
    }
}
