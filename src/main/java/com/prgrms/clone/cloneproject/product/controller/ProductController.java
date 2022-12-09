package com.prgrms.clone.cloneproject.product.controller;

import com.prgrms.clone.cloneproject.product.domain.Product;
import com.prgrms.clone.cloneproject.product.domain.ProductPutDTO;
import com.prgrms.clone.cloneproject.product.service.ProductProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/shop")
public class ProductController {

    private final ProductProvider productProvider;

    public ProductController(ProductProvider productProvider) {
        this.productProvider = productProvider;
    }

    @GetMapping("/products")
    public ResponseEntity getAllProduct(@RequestParam @Nullable String category, @RequestParam @Nullable Boolean isMade) {
        List<Product> productList = productProvider.getAll(category, isMade);
        return ResponseEntity.ok().body(productList);
    }

    @GetMapping("/products/{productId}")
    public ResponseEntity getOneProduct(@PathVariable Integer productId) {
        Product product = productProvider.getOne(productId);

        return ResponseEntity.ok().body(product);
    }

    @DeleteMapping("/products/{productId}")
    public ResponseEntity deleteProduct(@PathVariable Integer productId) {
        productProvider.delete(productId);

        return ResponseEntity.status(HttpStatus.OK).body("상품이 삭제되었습니다.");
    }

    @PutMapping("/products/{productId}")
    public ResponseEntity updateProduct(@RequestBody @Valid ProductPutDTO productPutDTO, @PathVariable Integer productId,
                                        UriComponentsBuilder uriComponentsBuilder) {

        productProvider.update(productId, productPutDTO);

        URI location = uriComponentsBuilder.path("/shop/products/{productId}")
                .buildAndExpand(productId).toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(location);

        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY).headers(headers).body("상품 이름이 변경되었습니다.");
    }
}
