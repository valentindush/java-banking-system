package com.java_ne.exceptions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.java_ne.dtos.response.ApiResponse;
import com.java_ne.utils.ExceptionUtils;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import jakarta.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
@EnableWebMvc
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiResponse<Object>> customException(CustomException ex, HttpServletResponse response) {
        if (!response.isCommitted()) {
            return ExceptionUtils.handleResponseException(ex.getException());
        }
        // Log the error if the response is already committed
        // You should replace this with your actual logging mechanism
        System.err.println("Response already committed. Unable to send error for CustomException: " + ex.getMessage());
        return null;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ApiResponse<Object>> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            Locale locale,
            HttpServletResponse response
    ) throws JsonProcessingException {
        if (!response.isCommitted()) {
            Map<String, String> errors = new HashMap<>();
            ex.getBindingResult().getAllErrors().forEach((error) -> {
                String fieldName = ((FieldError) error).getField();
                String errorMessage = error.getDefaultMessage();
                errors.put(fieldName, errorMessage);
            });
            return ApiResponse.error("Unprocessable Entity", HttpStatus.UNPROCESSABLE_ENTITY, errors);
        }
        // Log the error if the response is already committed
        System.err.println("Response already committed. Unable to send error for MethodArgumentNotValidException");
        return null;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleAllExceptions(Exception ex, WebRequest request, HttpServletResponse response) {
        if (!response.isCommitted()) {
            return ApiResponse.error("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        }
        // Log the error if the response is already committed
        System.err.println("Response already committed. Unable to send error for Exception: " + ex.getMessage());
        return null;
    }
}