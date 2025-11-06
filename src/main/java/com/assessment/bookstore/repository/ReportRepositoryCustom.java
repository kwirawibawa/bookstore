package com.assessment.bookstore.repository;

import java.util.List;

public interface ReportRepositoryCustom {

    Object[] getSalesReport();

    List<Object[]> getBestSellerBooks(int limit);

    Object[] getPriceReport();
}
