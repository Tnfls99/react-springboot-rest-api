package com.prgrms.clone.cloneproject.order.domain;

import com.prgrms.clone.cloneproject.customer.domain.CartItem;

import java.util.List;

public class OrderDTO {

    private Integer customerId;
    private List<CartItem> orderItems;

    public OrderDTO() {
    }

    public OrderDTO(Integer customerId, List<CartItem> orderItems) {
        this.customerId = customerId;
        this.orderItems = orderItems;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public List<CartItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<CartItem> orderItems) {
        this.orderItems = orderItems;
    }
}
