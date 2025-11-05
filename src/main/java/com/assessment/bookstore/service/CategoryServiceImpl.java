package com.assessment.bookstore.service;

import com.assessment.bookstore.model.entity.Category;
import com.assessment.bookstore.model.entity.User;
import com.assessment.bookstore.model.response.CategoryResponse;
import com.assessment.bookstore.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private CategoryResponse mapToResponse(Category category) {
        CategoryResponse response = new CategoryResponse();
        response.setId(category.getId().toString());
        response.setName(category.getName());
        response.setBooks(category.getBooks());
        return response;
    }

    @Override
    public CategoryResponse createCategory(Category category) {
        if (categoryRepository.existsByName(category.getName())) {
            throw new IllegalArgumentException("Category name already exists");
        }
        category.setCreatedAt(LocalDateTime.now());
        category.setCreatedBy(String.valueOf(User.Role.ADMIN));
        Category saved = categoryRepository.save(category);
        return mapToResponse(saved);
    }

    @Override
    public List<CategoryResponse> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        List<CategoryResponse> responses = new ArrayList<>();

        for (Category category : categories) {
            responses.add(mapToResponse(category));
        }

        return responses;
    }

    @Override
    public CategoryResponse updateCategory(UUID id, Category category) {
        Category existing = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));

        if (!existing.getName().equals(category.getName()) &&
                categoryRepository.existsByName(category.getName())) {
            throw new IllegalArgumentException("Category name already exists");
        }

        existing.setName(category.getName());
        existing.setUpdatedAt(LocalDateTime.now());
        existing.setUpdatedBy(String.valueOf(User.Role.ADMIN));

        Category updated = categoryRepository.save(existing);
        return mapToResponse(updated);
    }

    @Override
    public CategoryResponse deleteCategory(UUID id) {
        Category existing = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));

        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setId(existing.getId().toString());
        categoryResponse.setName(existing.getName());
        categoryResponse.setBooks(existing.getBooks());

        categoryRepository.delete(existing);

        return categoryResponse;
    }
}

