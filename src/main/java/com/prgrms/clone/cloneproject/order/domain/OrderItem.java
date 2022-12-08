package com.prgrms.clone.cloneproject.order.domain;


public class OrderItem {
    private Integer id;
    private Integer productId;
    private Integer price;
    private Integer quantity;

    public OrderItem() {
    }

    public OrderItem(Integer productId, Integer price, Integer quantity) {
        this.productId = productId;
        this.price = price;
        this.quantity = quantity;
    }

    public OrderItem(Integer id, Integer productId, Integer price, Integer quantity) {
        this.id = id;
        this.productId = productId;
        this.price = price;
        this.quantity = quantity;
    }

    public Integer getProductId() {
        return productId;
    }

    public Integer getPrice() {
        return price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
