package com.assessment.bookstore.repository;

import com.assessment.bookstore.model.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID>, BookRepositoryCustom {
    Optional<Book> findByTitleAndAuthor(String title, String author);
}
