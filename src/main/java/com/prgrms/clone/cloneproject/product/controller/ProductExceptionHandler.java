package com.prgrms.clone.cloneproject.product.controller;

import com.prgrms.clone.cloneproject.data.ErrorResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ProductExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(ProductExceptionHandler.class);

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResult> handleMethodArgumentNotValidException(HttpMessageNotReadableException httpMessageNotReadableException) {
        String errorDetail = "JSON 값이 유효하지 않습니다.";
        ErrorResult errorResult = new ErrorResult("데이터 형태 오류", errorDetail);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResult);
    }

    @ExceptionHandler(value = {IllegalArgumentException.class, IllegalStateException.class})
    public ResponseEntity<ErrorResult> handleInvalidParameterException(Exception exception) {
        ErrorResult errorResult = new ErrorResult("Illegal Exception", exception.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResult);
    }
}
