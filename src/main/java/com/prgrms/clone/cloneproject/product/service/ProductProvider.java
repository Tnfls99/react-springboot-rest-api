package com.prgrms.clone.cloneproject.product.service;

import com.prgrms.clone.cloneproject.product.domain.Product;
import com.prgrms.clone.cloneproject.product.domain.ProductDTO;
import com.prgrms.clone.cloneproject.product.domain.ProductPutDTO;
import com.prgrms.clone.cloneproject.product.domain.util.Category;
import com.prgrms.clone.cloneproject.product.domain.util.Color;
import com.prgrms.clone.cloneproject.product.repository.JdbcProductRepository;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductProvider {

    private final JdbcProductRepository jdbcProductRepository;

    public ProductProvider(JdbcProductRepository jdbcProductRepository) {
        this.jdbcProductRepository = jdbcProductRepository;
    }

    public List<Product> getAll(@Nullable String category, @Nullable Boolean isMade) {
        if (isNotNull(category)) {
            Category findCategory = Category.findCategory(category);
            return jdbcProductRepository.findByCategory(findCategory);
        }

        if (isNotNull(isMade) && isMade) {
            return jdbcProductRepository.findAllMadeProduct();
        }

        return jdbcProductRepository.findAll();
    }

    public Product getOne(Integer productId) {
        return jdbcProductRepository.findById(productId)
                .orElseThrow(() ->
                        new IllegalArgumentException("해당 Id에 해당하는 상품이 없습니다."));
    }

    public Product save(ProductDTO productDTO) {
        Product product = createProduct(productDTO);

        jdbcProductRepository.insert(product);
        return product;
    }

    public void delete(Integer productId) {
        jdbcProductRepository.deleteById(productId);
    }


    private boolean isNotNull(String param) {
        return param != null;
    }

    private boolean isNotNull(Boolean param) {
        return param != null;
    }

    public void update(Integer productId, ProductPutDTO productPutDTO) {
        Product product = jdbcProductRepository.findById(productId)
                .orElseThrow(() ->
                        new IllegalArgumentException("해당 Id에 해당하는 상품이 없습니다."));
        product.changeName(productPutDTO.getName());

        jdbcProductRepository.updateName(product);
    }

    private Product createProduct(ProductDTO productDTO) {

        Category category = Category.findCategory(productDTO.getCategory());
        Color color = Color.findColor(productDTO.getColor());

        return new Product(
                productDTO.getName(),
                category,
                productDTO.getPrice(),
                color,
                productDTO.getStock(),
                productDTO.getIsMade(),
                productDTO.getImageUrl()
        );
    }
}
