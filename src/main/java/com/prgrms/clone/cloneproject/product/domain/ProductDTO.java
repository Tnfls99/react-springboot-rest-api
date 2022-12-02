package com.prgrms.clone.cloneproject.product.domain;

public class ProductDTO {
    private String name;
    private Integer price;
    private String color;
    private String imageUrl;
    private Integer stock;
    private String category;
    private Boolean isMade;


    public ProductDTO(String name, Integer price, String color, String imageUrl, Integer stock, String category, Boolean isMade) {
        this.name = name;
        this.price = price;
        this.color = color;
        this.imageUrl = imageUrl;
        this.stock = stock;
        this.category = category;
        this.isMade = isMade;
    }

    public ProductDTO() {
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public String getColor() {
        return color;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Integer getStock() {
        return stock;
    }

    public String getCategory() {
        return category;
    }

    public Boolean getIsMade() {
        return isMade;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setMade(Boolean made) {
        isMade = made;
    }
}
