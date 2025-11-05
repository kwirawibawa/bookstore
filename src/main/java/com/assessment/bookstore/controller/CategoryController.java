package com.assessment.bookstore.controller;

import com.assessment.bookstore.model.entity.Category;
import com.assessment.bookstore.model.response.ApiResponse;
import com.assessment.bookstore.model.response.CategoryResponse;
import com.assessment.bookstore.service.CategoryService;
import com.assessment.bookstore.util.CommonConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<CategoryResponse>> createCategory(@RequestBody Category category) {
        return ResponseEntity.ok(new ApiResponse<>(CommonConstant.SUCCESS_CODE,
                "Category created successfully", categoryService.createCategory(category)));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<CategoryResponse>>> getCategories() {
        return ResponseEntity.ok(new ApiResponse<>(CommonConstant.SUCCESS_CODE,
                "Categories fetched successfully", categoryService.getAllCategories()));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<CategoryResponse>> updateCategory(@PathVariable UUID id, @RequestBody Category category) {
        return ResponseEntity.ok(new ApiResponse<>(CommonConstant.SUCCESS_CODE,
                "Category updated successfully", categoryService.updateCategory(id, category)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteCategory(@PathVariable UUID id) {
        CategoryResponse categoryResponse = categoryService.deleteCategory(id);
        return ResponseEntity.ok(new ApiResponse<>(CommonConstant.SUCCESS_CODE,
                "Category " + categoryResponse.getName() + "is deleted successfully", null));
    }
}

