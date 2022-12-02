package com.prgrms.clone.cloneproject.product.controller;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ProductExceptionHandler {

    @ExceptionHandler(value = {IllegalArgumentException.class, MethodArgumentNotValidException.class})
    protected String addProductExceptionHandler(Exception e, HttpServletRequest request){
        System.out.println(request.getRequestURI());
        return null;
    }
}
