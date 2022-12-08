package com.prgrms.clone.cloneproject.product.domain;

public class ProductPutDTO {
    private String name;


    public ProductPutDTO(String name) {
        this.name = name;
    }

    public ProductPutDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
