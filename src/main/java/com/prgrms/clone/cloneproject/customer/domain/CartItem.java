package com.prgrms.clone.cloneproject.customer.domain;

public class CartItem {
    private static final Integer BEFORE_INSERT = null;

    private Integer id;
    private final Integer cartId;
    private final Integer productId;
    private final Integer quantity;

    public CartItem(Integer id, Integer cartId, Integer productId, Integer quantity) {
        this.id = id;
        this.cartId = cartId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public CartItem(Integer cartId, Integer productId, Integer quantity) {
        this(null, cartId, productId, quantity);
    }

    public Integer getId() {
        return id;
    }

    public Integer getCartId() {
        return cartId;
    }

    public Integer getProductId() {
        return productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setId(Integer id){
        if(id != BEFORE_INSERT){
            throw new IllegalArgumentException("id 값은 변경할 수 없습니다.");
        }
        this.id = id;
    }
}
