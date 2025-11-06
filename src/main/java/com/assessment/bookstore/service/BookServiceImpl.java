package com.assessment.bookstore.service;

import com.assessment.bookstore.exception.DuplicateException;
import com.assessment.bookstore.model.entity.Book;
import com.assessment.bookstore.model.entity.Category;
import com.assessment.bookstore.model.request.BookRequest;
import com.assessment.bookstore.model.request.BookSearchRequest;
import com.assessment.bookstore.model.response.BookResponse;
import com.assessment.bookstore.model.response.SearchResponse;
import com.assessment.bookstore.repository.BookRepository;
import com.assessment.bookstore.repository.CategoryRepository;
import com.assessment.bookstore.util.CommonConstant;
import com.assessment.bookstore.util.CommonPaging;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public SearchResponse<BookResponse> searchBooks(BookSearchRequest request) {
        Long total = bookRepository.searchCount(request);
        List<BookResponse> data = bookRepository.searchData(request);

        CommonPaging paging = new CommonPaging(request.getPageNumber(), request.getPageSize(), total);

        return new SearchResponse<>(
                request.getPageNumber(),
                request.getPageSize(),
                paging.getNumberOfElements(),
                total,
                (long) Math.ceil((double) total / request.getPageSize()),
                paging.getStartData(),
                paging.getEndData(),
                data,
                CommonConstant.SUCCESS_CODE,
                CommonConstant.SUCCESS
        );
    }

    @Transactional
    @Override
    public BookResponse createBook(BookRequest request) {
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));

        bookRepository.findByTitleAndAuthor(request.getTitle(), request.getAuthor())
                .ifPresent(b -> {
                    throw new DuplicateException("Book with the same title and author already exists");
                });

        Book book = Book.builder()
                .title(request.getTitle())
                .author(request.getAuthor())
                .price(request.getPrice())
                .stock(request.getStock())
                .publishYear(request.getPublishYear())
                .category(category)
                .imageBase64(request.getImageBase64())
                .build();

        book = bookRepository.save(book);

        return mapToResponse(book);
    }

    @Override
    @Transactional(readOnly = true)
    public BookResponse getBookById(UUID id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book " + CommonConstant.NOT_FOUND));
        return mapToResponse(book);
    }

    @Transactional
    @Override
    public BookResponse updateBook(UUID id, BookRequest request) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book " + CommonConstant.NOT_FOUND));

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));

        bookRepository.findByTitleAndAuthor(request.getTitle(), request.getAuthor())
                .filter(b -> !b.getId().equals(id))
                .ifPresent(b -> {
                    throw new DuplicateException("Book with the same title and author already exists");
                });

        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setPrice(request.getPrice());
        book.setStock(request.getStock());
        book.setPublishYear(request.getPublishYear());
        book.setCategory(category);
        book.setImageBase64(request.getImageBase64());

        book = bookRepository.save(book);
        return mapToResponse(book);
    }

    @Transactional
    @Override
    public BookResponse deleteBook(UUID id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book not found"));

        bookRepository.delete(book);

        return BookResponse.builder()
                .id(String.valueOf(book.getId()))
                .title(book.getTitle())
                .build();
    }

    private BookResponse mapToResponse(Book book) {
        return new BookResponse(
                book.getId().toString(),
                book.getTitle(),
                book.getAuthor(),
                book.getPrice(),
                book.getStock(),
                book.getPublishYear(),
                book.getCategory().getId().toString(),
                book.getCategory().getName(),
                book.getImageBase64()
        );
    }
}
