package com.prgrms.clone.cloneproject.product.repository;

import com.prgrms.clone.cloneproject.product.domain.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    List<Product> findAll();

    Optional<Product> findById(Integer productId);

    void insert(Product product);

    void deleteById(Integer productId);
}
