package com.prgrms.clone.cloneproject.order.domain;

import java.util.List;

public class OrderDTO {
    private String address;
    private String email;
    private List<OrderItem> orderItems;

    public OrderDTO() {
    }

    public OrderDTO(String address, String email, List<OrderItem> orderItems) {
        this.address = address;
        this.email = email;
        this.orderItems = orderItems;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}
