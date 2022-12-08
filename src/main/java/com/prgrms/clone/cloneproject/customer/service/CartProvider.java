package com.prgrms.clone.cloneproject.customer.service;

import com.prgrms.clone.cloneproject.customer.repository.CartRepository;
import com.prgrms.clone.cloneproject.product.domain.Product;

public class CartProvider {

    private final CartRepository jdbcCartRepository;

    public CartProvider(CartRepository jdbcCartRepository) {
        this.jdbcCartRepository = jdbcCartRepository;
    }

    public void addCartItem(Product product, Integer quantity){
        
    }
}
