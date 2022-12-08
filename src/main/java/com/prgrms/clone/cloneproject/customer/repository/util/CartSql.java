package com.prgrms.clone.cloneproject.customer.repository.util;

public class CartSql {
    private static final String FIND_CART_ITEMS = "SELECT * FROM cart_items WHERE cart_id = :cart_id";
    private static final String FIND_CART =
            "SELECT * FROM cart LEFT JOIN customer on cart.customer_id = customer.id WHERE cart.customer_id = :id";


    public static String getFindCartItemsQuery(){
        return FIND_CART_ITEMS;
    }

    public static String getFindCartQuery(){
        return FIND_CART;
    }
}
