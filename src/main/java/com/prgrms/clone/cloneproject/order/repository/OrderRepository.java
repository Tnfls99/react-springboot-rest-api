package com.prgrms.clone.cloneproject.order.repository;

import com.prgrms.clone.cloneproject.order.domain.Order;

import java.util.Optional;

public interface OrderRepository {

    void insert(Order order);

    Optional<Order> findById(Integer orderId);
}
