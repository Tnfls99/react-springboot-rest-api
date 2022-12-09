package com.prgrms.clone.cloneproject.customer.repository.util;

public class CartSql {
    private static final String FIND_CART_ITEMS = "SELECT * FROM cart_items WHERE cart_id = :cart_id";
    private static final String FIND_CART =
            "SELECT * FROM cart LEFT JOIN customer on cart.customer_id = customer.id WHERE cart.customer_id = :id";
    private static final String FIND_CART_BY_CUSTOMER = "SELECT * FROM cart WHERE customer_id = :customer_id";
    private static final String INSERT_CART_ITEM = "INSERT INTO cart_items(cart_id, product_id, price, quantity) VALUES (:cart_id, :product_id, :price, :quantity)";
    private static final String INSERT_CART = "INSERT INTO cart(customer_id) VALUES (:customer_id)";
    private static final String UPDATE_ITEM = "UPDATE cart_items SET quantity = :quantity WHERE id = :id";


    public static String getFindCartItemsQuery(){
        return FIND_CART_ITEMS;
    }

    public static String getFindCartQuery(){
        return FIND_CART;
    }

    public static String getFindCartByCustomerId() {
        return FIND_CART_BY_CUSTOMER;
    }

    public static String getInsertCartItemQuery(){
        return INSERT_CART_ITEM;
    }

    public static String getInsertCartQuery(){
        return INSERT_CART;
    }

    public static String getUpdateItemQuery() {
        return UPDATE_ITEM;
    }
}
