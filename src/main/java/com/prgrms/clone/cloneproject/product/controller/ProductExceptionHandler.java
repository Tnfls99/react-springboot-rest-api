package com.prgrms.clone.cloneproject.product.controller;

import com.prgrms.clone.cloneproject.customer.exception.DuplicateUserException;
import com.prgrms.clone.cloneproject.customer.exception.NoAuthenticatedException;
import com.prgrms.clone.cloneproject.data.ErrorResult;
import com.prgrms.clone.cloneproject.order.service.NoOrderException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ProductExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResult> handleMethodArgumentNotValidException(HttpMessageNotReadableException httpMessageNotReadableException) {
        ErrorResult errorResult = new ErrorResult("데이터 형태 오류", httpMessageNotReadableException.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResult);
    }

    @ExceptionHandler(value = {IllegalArgumentException.class, IllegalStateException.class})
    public ResponseEntity<ErrorResult> handleInvalidParameterException(Exception exception) {
        ErrorResult errorResult = new ErrorResult("Illegal Exception", exception.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResult);
    }

    @ExceptionHandler(NoOrderException.class)
    public ResponseEntity<ErrorResult> handleNoOrderException(NoOrderException noOrderException) {
        ErrorResult errorResult = new ErrorResult("No order", noOrderException.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResult);
    }

    @ExceptionHandler(NoAuthenticatedException.class)
    public ResponseEntity<ErrorResult> handleNoAuthentivatedException(NoAuthenticatedException noAuthenticatedException) {
        ErrorResult errorResult = new ErrorResult("Not valid cuatomer", noAuthenticatedException.getMessage());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResult);
    }

    @ExceptionHandler(DuplicateUserException.class)
    public ResponseEntity<ErrorResult> handleDulplicateException(DuplicateUserException duplicateUserException) {
        ErrorResult errorResult = new ErrorResult("중복값입니다.", duplicateUserException.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResult);
    }
}
