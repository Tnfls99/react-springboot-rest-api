package com.prgrms.clone.cloneproject.customer.domain.dto;

import java.util.List;

public class CartDTO {
    private Integer customerId;
    private List<CartItemDTO> cartItems;

    public CartDTO() {
    }

    public CartDTO(Integer customerId, List<CartItemDTO> cartItems) {
        this.customerId = customerId;
        this.cartItems = cartItems;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public List<CartItemDTO> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItemDTO> cartItems) {
        this.cartItems = cartItems;
    }
}
