package com.prgrms.clone.cloneproject.customer.domain.dto;

public class CartItemDTO {
    private Integer productId;
    private Integer price;
    private Integer quantity;

    public CartItemDTO() {
    }

    public CartItemDTO(Integer productId, Integer price, Integer quantity) {
        this.productId = productId;
        this.price = price;
        this.quantity = quantity;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
