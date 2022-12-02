package com.prgrms.clone.cloneproject.repository.util;

public class ProductSql {

    private static final String INSERT = "INSERT INTO product(name, price, category, color, stock, registered_at, is_made, image_url) VALUES (:name, :price, :category, :color, :stock, :registered_at, :is_made, :image_url)";
    private static final String FIND_BY_ID = "SELECT * FROM product WHERE id= :id";
    private static final String FIND_ALL = "SELECT * FROM product";
    private static final String UPDATE_NAME = "UPDATE product SET name = :name WHERE id = :id";
    private static final String DELETE = "DELETE FROM product WHERE id = :id";
    private static final String FIND_ALL_BY_LOW_PRICE = "SELECT * FROM product ORDER BY price";
    private static final String FIND_ALL_BY_HIGH_PRICE = "SELECT * FROM product ORDER BY price DESC";
    private static final String FIND_ALL_BY_NAME = "SELECT * FROM product ORDER BY name";
    private static final String FIND_BY_ALL_BY_REGISTERED_AT = "SELECT * FROM product ORDER BY registered_at DESC";
    private static final String FIND_MADE = "SELECT * FROM product WHERE is_made=true";
    private static final String FIND_BY_CATEGORY = "SELECT * FROM product WHERE category=:category";


    public static String getFindAllQuery(){
        return FIND_ALL;
    }

    public static String getFindByIdQuery(){
        return FIND_BY_ID;
    }

    public static String getInsertQuery(){
        return INSERT;
    }

    public static String getUpdateNameQuery() {
        return UPDATE_NAME;
    }

    public static String getDeleteQuery() {
        return DELETE;
    }

    public static String getFindAllByLowPriceQuery(){
        return FIND_ALL_BY_LOW_PRICE;
    }

    public static String getFindAllByHighPriceQuery() {
        return FIND_ALL_BY_HIGH_PRICE;
    }

    public static String getFindAllByNameQuery() {
        return FIND_ALL_BY_NAME;
    }

    public static String getFindAllByRegisteredAtQuery() {
        return FIND_BY_ALL_BY_REGISTERED_AT;
    }

    public static String getFindAllMadeProductQuery() {
        return FIND_MADE;
    }

    public static String getFindByCategoryQuery() {
        return FIND_BY_CATEGORY;
    }
}
