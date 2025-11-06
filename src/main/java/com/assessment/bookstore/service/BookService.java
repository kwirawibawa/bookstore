package com.assessment.bookstore.service;

import com.assessment.bookstore.model.request.BookRequest;
import com.assessment.bookstore.model.request.BookSearchRequest;
import com.assessment.bookstore.model.response.BookResponse;
import com.assessment.bookstore.model.response.SearchResponse;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface BookService {
    SearchResponse<BookResponse> searchBooks(BookSearchRequest request);

    @Transactional
    BookResponse createBook(BookRequest request);

    BookResponse getBookById(UUID id);

    @Transactional
    BookResponse updateBook(UUID id, BookRequest request);

    @Transactional
    BookResponse deleteBook(UUID id);
}
