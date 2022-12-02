package com.prgrms.clone.cloneproject.product.service;

import com.prgrms.clone.cloneproject.product.domain.Product;
import com.prgrms.clone.cloneproject.product.domain.ProductDTO;
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

    public List<Product> getAll(@Nullable String category){
        if(isNull(category)){
            return jdbcProductRepository.findAll();
        }

        Category findCategory = Category.findCategory(category);
        return jdbcProductRepository.findByCategory(findCategory);
    }

    public Product getOne(Integer productId){
        return jdbcProductRepository.findById(productId)
                .orElseThrow(() ->
                        new IllegalArgumentException("해당 Id에 해당하는 상품이 없습니다."));
    }

    private boolean isNull(String param){
        return param == null;
    }

    public Product save(ProductDTO productDTO) {
        String productName = productDTO.getName();
        Integer price = productDTO.getPrice();
        Color color = Color.findColor(productDTO.getColor());
        Category category = Category.findCategory(productDTO.getCategory());
        Boolean isMade = productDTO.getIsMade();
        String imageUrl = productDTO.getImageUrl();
        Integer stock = productDTO.getStock();

        Product product = new Product(
                productName, category, price, color, stock, isMade, imageUrl
        );

        jdbcProductRepository.insert(product);
        return product;
    }
}
