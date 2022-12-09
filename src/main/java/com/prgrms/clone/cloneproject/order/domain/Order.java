package com.prgrms.clone.cloneproject.order.domain;

import com.prgrms.clone.cloneproject.order.domain.util.OrderStatus;

import java.util.List;

public class Order {
    private Integer id;
    private Integer customerId;
    private List<OrderItem> orderItems;
    private OrderStatus orderStatus;

    public Order(Integer id, Integer customerId, List<OrderItem> orderItems, OrderStatus orderStatus) {
        validOrderList(orderItems);
        this.id = id;
        this.customerId = customerId;
        this.orderItems = orderItems;
        this.orderStatus = orderStatus;
    }

    public Order(Integer customerId, List<OrderItem> orderItems, OrderStatus orderStatus) {
        this(null, customerId, orderItems, orderStatus);
    }

    public Integer getId() {
        return id;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    private void validOrderList(List<OrderItem> productList) {
        if (productList.size() == 0) {
            throw new IllegalArgumentException("상품 없이 주문하는 것은 불가능합니다.");
        }
    }

    public void setId(Integer generatedKey) {
        if (isIdNotNull()) {
            throw new IllegalStateException("id 값은 변경할 수 없습니다.");
        }
        id = generatedKey;
    }

    private boolean isIdNotNull() {
        return id != null;
    }
}
