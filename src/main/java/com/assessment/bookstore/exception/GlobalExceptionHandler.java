package com.assessment.bookstore.exception;

import com.assessment.bookstore.model.response.ApiResponse;
import com.assessment.bookstore.util.CommonConstant;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.OptimisticLockException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

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

    @ExceptionHandler(OptimisticLockException.class)
    public ResponseEntity<ApiResponse<String>> handleOptimisticLock(OptimisticLockException ex) {
        log.error("Optimistic Error: ", ex);
        ApiResponse<String> response = new ApiResponse<>(
                CommonConstant.CONFLICT_CODE,
                "Data has been modified by another transaction, please retry again",
                null
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiResponse<String>> handleNotFound(EntityNotFoundException ex) {
        ApiResponse<String> response = new ApiResponse<>(
                CommonConstant.NOT_FOUND_CODE,
                ex.getMessage(),
                null
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(DuplicateException.class)
    public ResponseEntity<ApiResponse<String>> handleDuplicateBook(DuplicateException ex) {
        ApiResponse<String> response = new ApiResponse<>(
                CommonConstant.CONFLICT_CODE,
                ex.getMessage(),
                null
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(PaymentFailedException.class)
    public ResponseEntity<ApiResponse<String>> handlePaymentFailed(PaymentFailedException ex) {
        ApiResponse<String> response = new ApiResponse<>(
                CommonConstant.BAD_REQUEST_CODE,
                ex.getMessage(),
                null
        );
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(UnauthorizedActionException.class)
    public ResponseEntity<ApiResponse<String>> handleUnauthorizedExceptions(UnauthorizedActionException ex) {
        ApiResponse<String> response = new ApiResponse<>(
                CommonConstant.FORBIDDEN_CODE,
                ex.getMessage(),
                null
        );
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(OrderPaidException.class)
    public ResponseEntity<ApiResponse<String>> handleOrderExceptions(OrderPaidException ex) {
        ApiResponse<String> response = new ApiResponse<>(
                CommonConstant.BAD_REQUEST_CODE,
                ex.getMessage(),
                null
        );
        return ResponseEntity.badRequest().body(response);
    }
}