package com.assessment.bookstore.repository;

import com.assessment.bookstore.model.request.BookSearchRequest;
import com.assessment.bookstore.model.response.BookResponse;

import java.util.List;

public interface BookRepositoryCustom {
    Long searchCount(BookSearchRequest request);
    List<BookResponse> searchData(BookSearchRequest request);
}
