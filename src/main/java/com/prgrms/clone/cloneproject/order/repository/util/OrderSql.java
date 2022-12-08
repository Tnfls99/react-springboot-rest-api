package com.prgrms.clone.cloneproject.order.repository.util;

public class OrderSql {
    private static final String ORDER_INSERT = "INSERT INTO orders(email, address, order_status) VALUES (:email, :address, :orderStatus)";
    private static final String ORDER_ITEM_INSERT = "INSERT INTO order_items(order_id, product_id, price, quantity) " +
            "VALUES (:orderId, :productId, :price, :quantity)";
    private static final String ORDER_FIND_ONE = "SELECT * FROM orders WHERE id = :id";
    private static final String ORDER_ITEMS = "SELECT * FROM order_items LEFT JOIN orders on orders.id = order_items.order_id WHERE order_items.order_id = :id";

    public static String getOrderInsertQuery() {
        return ORDER_INSERT;
    }

    public static String getOrderItemInsertQuery() {
        return ORDER_ITEM_INSERT;
    }

    public static String getOrderFindOneQuery() {
        return ORDER_FIND_ONE;
    }

    public static String getOrderItems() {
        return ORDER_ITEMS;
    }
}
