package com.assessment.bookstore.service;

import com.assessment.bookstore.model.entity.Category;
import com.assessment.bookstore.model.response.CategoryResponse;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    @Transactional
    CategoryResponse createCategory(Category category);

    List<CategoryResponse> getAllCategories();

    @Transactional
    CategoryResponse updateCategory(UUID id, Category category);

    @Transactional
    CategoryResponse deleteCategory(UUID id);
}
