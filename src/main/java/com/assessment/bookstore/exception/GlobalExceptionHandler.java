package com.assessment.bookstore.exception;

import com.assessment.bookstore.model.response.ApiResponse;
import com.assessment.bookstore.util.CommonConstant;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Validation errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

        ApiResponse<Map<String, String>> response = new ApiResponse<>(
                CommonConstant.BAD_REQUEST_CODE,
                CommonConstant.VALIDATION_ERROR,
                errors
        );

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ApiResponse<String>> handleIllegalState(IllegalStateException ex) {
        ApiResponse<String> response = new ApiResponse<>(
                CommonConstant.BAD_REQUEST_CODE,
                ex.getMessage(),
                null
        );

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> handleAllExceptions(Exception ex) {
        ApiResponse<String> response = new ApiResponse<>(
                CommonConstant.INTERNAL_ERROR_CODE,
                CommonConstant.INTERNAL_SERVER_ERROR,
                ex.getMessage()
        );

        return ResponseEntity.status(CommonConstant.INTERNAL_ERROR_CODE).body(response);
    }
}