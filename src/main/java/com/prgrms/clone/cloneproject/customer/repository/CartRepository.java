package com.prgrms.clone.cloneproject.customer.repository;

import com.prgrms.clone.cloneproject.customer.domain.Cart;
import com.prgrms.clone.cloneproject.customer.domain.CartItem;

import java.util.Optional;

public interface CartRepository {

    void insertCartItem(CartItem cartItem);

    Optional<Cart> findCartById(Integer cartId);

}
