package com.prgrms.clone.cloneproject.customer.repository.util;

public class CustomerSql {
    private static final String INSERT = "INSERT INTO customer(name, email, address) VALUES (:name, :email, :address)";
    private static final String FIND_BY_ID = "SELECT * FROM customer WHERE id = :id";
    private static final String FIND_ALL = "SELECT * FROM customer";
    private static final String CHANGE_NAME = "UPDATE customer SET name = :name WHERE id = :id";
    private static final String DELETE = "DELETE FROM customer WHERE id= :id";
    private static final String COUNT = "SELECT COUNT(*) FROM customer WHERE email = :email";
    private static final String CHANGE_EMAIL = "UPDATE customer SET email = :email WHERE id = :id";
    private static final String CHANGE_ADDRESS = "UPDATE customer SET address = :address WHERE id = :id";

    public static String getInsertQuery() {
        return INSERT;
    }

    public static String getFindByIdQuery() {
        return FIND_BY_ID;
    }

    public static String getAllQuery() {
        return FIND_ALL;
    }

    public static String getChangeNameQuery() {
        return CHANGE_NAME;
    }

    public static String getDeleteQuery() {
        return DELETE;
    }

    public static String getCountUserQuery() {
        return COUNT;
    }

    public static String getChangeEmailQuery() {
        return CHANGE_EMAIL;
    }

    public static String getChangeAddresQuery() {
        return CHANGE_ADDRESS;
    }
}
