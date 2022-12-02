package com.prgrms.clone.cloneproject.repository;

import com.prgrms.clone.cloneproject.domain.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    List<Product> findAll();

    Optional<Product> findById(Integer productId);

    void insert(Product product);

    void deleteById(Integer productId);
}
