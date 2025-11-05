package com.assessment.bookstore.service;

import com.assessment.bookstore.model.entity.Category;
import com.assessment.bookstore.model.response.CategoryResponse;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    CategoryResponse createCategory(Category category);

    List<CategoryResponse> getAllCategories();

    CategoryResponse updateCategory(UUID id, Category category);

    CategoryResponse deleteCategory(UUID id);
}
