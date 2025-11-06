package com.assessment.bookstore.service;

import com.assessment.bookstore.model.entity.Category;
import com.assessment.bookstore.model.entity.User;
import com.assessment.bookstore.model.response.CategoryResponse;
import com.assessment.bookstore.repository.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
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

    @Transactional
    @Override
    public CategoryResponse updateCategory(UUID id, Category category) {
        Category existing = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));

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

    @Transactional
    @Override
    public CategoryResponse deleteCategory(UUID id) {
        Category existing = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));

        categoryRepository.delete(existing);

        return CategoryResponse.builder()
                .id(existing.getId().toString())
                .name(existing.getName())
                .books(existing.getBooks())
                .build();
    }
}

