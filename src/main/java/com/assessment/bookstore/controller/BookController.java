package com.assessment.bookstore.controller;

import com.assessment.bookstore.model.request.BookRequest;
import com.assessment.bookstore.model.request.BookSearchRequest;
import com.assessment.bookstore.model.response.ApiResponse;
import com.assessment.bookstore.model.response.BookResponse;
import com.assessment.bookstore.model.response.SearchResponse;
import com.assessment.bookstore.service.BookService;
import com.assessment.bookstore.util.CommonConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public SearchResponse<BookResponse> searchBooks(@Validated @ModelAttribute BookSearchRequest request) {
        return bookService.searchBooks(request);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<BookResponse> createBook(@Validated @RequestBody BookRequest request) {
        return new ApiResponse<>(CommonConstant.SUCCESS_CODE, "Book created successfully", bookService.createBook(request));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ApiResponse<BookResponse> getBook(@PathVariable UUID id) {
        return new ApiResponse<>(CommonConstant.SUCCESS_CODE, "Book found", bookService.getBookById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<BookResponse> updateBook(@PathVariable UUID id,
                                                @Validated @RequestBody BookRequest request) {
        return new ApiResponse<>(CommonConstant.SUCCESS_CODE, "Book updated successfully", bookService.updateBook(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Void> deleteBook(@PathVariable UUID id) {
        BookResponse book = bookService.deleteBook(id);
        return new ApiResponse<>(200, "Book with title " + book.getTitle() + " is deleted successfully", null);
    }
}
