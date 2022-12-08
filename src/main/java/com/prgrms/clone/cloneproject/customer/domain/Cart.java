package com.prgrms.clone.cloneproject.customer.domain;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private static final Integer BEFORE_INSERT = null;

    private final Integer customerId;

    private Integer id;
    private List<CartItem> cartItems;

    public Cart(Integer id, Integer customerId, List<CartItem> cartItems) {
        this.id = id;
        this.customerId = customerId;
        this.cartItems = cartItems;
    }

    public Cart(Integer customerId) {
        this(null, customerId, new ArrayList<>());
    }

    public void add(Integer productId, Integer quantity) {
        this.cartItems.add(new CartItem(id, productId, quantity));
    }

    public void deleteProduct(CartItem cartItem) {
        if (isNotContain(cartItem)) {
            throw new IllegalArgumentException("장바구니에 해당 상품이 없습니다. 잘못된 요청입니다.");
        }
        cartItems.remove(cartItem);
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setId(Integer id) {
        if (id != BEFORE_INSERT) {
            throw new IllegalArgumentException("id 값은 변경할 수 없습니다.");
        }
        this.id = id;
    }

    private boolean isNotContain(CartItem cartItem) {
        return cartItems.contains(cartItem);
    }

}
